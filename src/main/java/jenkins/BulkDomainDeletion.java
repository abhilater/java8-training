package jenkins;

import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.JenkinsTriggerHelper;
import com.offbytwo.jenkins.model.BuildResult;
import com.offbytwo.jenkins.model.BuildWithDetails;
import com.offbytwo.jenkins.model.JobWithDetails;
import com.opencsv.CSVReader;
import java.io.FileReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

public class BulkDomainDeletion {

  final static Logger LOG = Logger.getLogger(BulkDomainDeletion.class);

  private static final String JENKINS_URL = "https://igor.p.helpshift.com/job/apollo-phdp";
  private static final String JENKINS_USER = "abhishek.gupta@helpshift.com";
  private static final String JENKINS_SECRET_TOKEN = "a0a4316d4261f9bde4ca4369c180da9b";

  public static void main(String[] args) throws Exception {

    JenkinsServer jenkins = createJenkinsClient();
    List<String> domainsToBeDeleted = extractDomainsForDeletion();

    JobWithDetails job = jenkins.getJob("gdpr-domain-redaction");
    JenkinsTriggerHelper helper = new JenkinsTriggerHelper(jenkins, 5000L);

    int index = 1;
    for (String domain : domainsToBeDeleted) {

      LOG.info("[STARTED] Domain deletion job for domain: " + domain + ", index: " + index++);
      BuildWithDetails build = helper
          .triggerJobAndWaitUntilFinished("gdpr-domain-redaction", Map.of("DOMAIN", domain));
      if (build.getResult().equals(BuildResult.SUCCESS)) {
        LOG.info("[SUCCEEDED] Domain deletion job for domain: " + domain + ", index: " + index);
      } else {
        LOG.info(
            "[FAILED] Domain deletion job for domain: " + domain + ", index: " + index + ", cause: "
                + build.getResult());
        System.exit(100);
      }
    }
  }

  static JenkinsServer createJenkinsClient() throws Exception {
    return new JenkinsServer(new URI(JENKINS_URL), JENKINS_USER, JENKINS_SECRET_TOKEN);
  }

  static List<String> extractDomainsForDeletion() throws Exception {
    List<String> domains = new ArrayList<String>();
    try (CSVReader csvReader = new CSVReader(
        new FileReader("/Users/abhishek/Downloads/CM-6702/freetrial_domains_deletion.csv"),
        ',', '\'', 1)) {
      String[] values = null;
      while ((values = csvReader.readNext()) != null) {
        domains.add(values[0]);
      }
    }
    Collections.sort(domains);
    return domains;
  }

}
