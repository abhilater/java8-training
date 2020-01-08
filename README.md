HS4P - Amazon Connect Integration Guide 

[[TOC]]

## About

This document lists the steps to be followed for enabling Amazon Connect Integration within your Helpshift domain.

## Prerequisites

<table>
  <tr>
    <td>#</td>
    <td>Component</td>
    <td>Comments</td>
  </tr>
  <tr>
    <td>1</td>
    <td>Amazon Connect Instance

(eg: hstest.awsapps.com)</td>
    <td>The Amazon Connect instance you will use for your Voice Support. Additional flows will need to be configured  as part of the integration (more details in subsequent sections). 
A user with admin privileges on Amazon Connect.</td>
  </tr>
  <tr>
    <td>2</td>
    <td>Helpshift Domain

(eg: test.helpshift.com)</td>
    <td>A Helpshift instance that will be used for Messaging and CTI Integration</td>
  </tr>
  <tr>
    <td>3</td>
    <td>Helpshift App

</td>
    <td>A Helpshift App to which all the Voice issues will be mapped to. For IVR to Messaging Deflection, a Web Chat platform will need to be added for that App, and the "Show Web Chat Widget" toggle should be enabled.https://support.helpshift.com/kb/article/web-chat/
</td>
  </tr>
  <tr>
    <td>4</td>
    <td>Enable - Voice Integration</td>
    <td>Voice Integration Feature must be enabled for the domain. Please talk to your Account Manager about enabling this feature</td>
  </tr>
  <tr>
    <td>5</td>
    <td>Enable - Custom Bots</td>
    <td>Helpshift’s Amazon Connect Integration requires that  Bots with APIs are enabled for your Helpshift domain. Please talk to your Account Manager about enabling this feature</td>
  </tr>
  <tr>
    <td>6</td>
    <td>AWS Region ID</td>
    <td>Please share the AWS Region ID for the AWS instance used for Amazon Connect if it is not one of the following: us-east-1, us-east-2, us-west-2, eu-west-2.</td>
  </tr>
  <tr>
    <td>7</td>
    <td>AWS user with privileges</td>
    <td>For deploying the Lambda function for IVR to Messaging deflection the AWS user should either have administrator privileges or have the read/write privileges on the following AWS resources
Lambda
S3
Cloudformation
IAM
Kinesis</td>
  </tr>
</table>


#### Initialize your AWS stack using Cloudformation

Use the following "AWS Cloudformation" template link to create the stack using the stack builder as demonstrated in the following steps

[https://hs-voice-integration-<AWS-REGION-ID>.s3.amazonaws.com/cloudformationResource/hs-voice-integration.template](https://hs-voice-integration.s3-us-west-2.amazonaws.com/cloudformationResource/hs-voice-integration.template)

AWS-REGION-ID needs to be substituted with your AWS region ID e.g. us-east-1, us-east-2, us-west-2, eu-west-2  **<< Amazon doc on regions>>**
