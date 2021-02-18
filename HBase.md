# HBase Performance tests

### Commands
* https://github.com/brianfrankcooper/YCSB/tree/master/hbase1
* https://mapr.com/support/s/article/Performance-testing-HBase-Mapr-db-Binary-table?language=en_US

```
hbase pe --table="dataplat_analytics:loadtesttable3"  sequentialWrite  1
hbase pe --table="dataplat_analytics:loadtesttable3" --rows=10000 --nomapred    randomRead  4
hbase pe --table="dataplat_analytics:loadtesttable2" --rows=10000 --nomapred    randomRead  4
hbase pe --table="dataplat_analytics:loadtesttable1" --rows=10000 --nomapred    randomRead  2
hbase pe --table="dataplat_analytics:loadtesttable1" --rows=1000000 --nomapred  scanRange100  4
hbase pe --table="dataplat_analytics:obfuscation_mappings" --rows=1000000 --nomapred  randomRead  4


n_splits = 50
create 'dataplat_analytics:usertable_10M', 'family', {SPLITS => (1..n_splits).map {|i| "user#{1000+i*(9999-1000)/n_splits}"}}

bin/ycsb load hbase12 -P workloads/workloada -cp /etc/hbase/conf -p table=dataplat_analytics:usertable_10M -p columnfamily=family



move_rsgroup_tables 'obfuscation_group', ['dataplat_analytics:agent_issue_events','dataplat_analytics:agent_live_aggregates','dataplat_analytics:loadtesttable1','dataplat_analytics:loadtesttable2','dataplat_analytics:loadtesttable3','dataplat_analytics:open_issues_state','dataplat_analytics:predict_aggregates','dataplat_analytics:queue_issue_events','dataplat_analytics:queue_live_aggregates','dataplat_analytics:sensai_predict_domain_metrics','dataplat_analytics:sensai_predict_metrics']
```