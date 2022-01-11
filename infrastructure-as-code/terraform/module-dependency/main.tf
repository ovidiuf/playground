#
# Input variables are declared in variables.tf
#

#
# Output variables are declared in outpus.tf
#

#
# Constrain Terraform version in case version-specific features are used
#
terraform {

  required_version = ">= 0.12"
}

provider "aws" {

  profile = "default"
  region  = "us-west-2"
}

module "c3-cluster" {

  source       = "terraform-aws-modules/eks/aws"
  version      = "~> 9.0"

//  cluster_name = local.cluster_name
//
//  vpc_id                               = var.aws_vpc_id
//  subnets                              = var.subnets
//  worker_additional_security_group_ids = concat([aws_security_group.allow_nfs.id], var.worker_security_group_ids)
//
//  write_kubeconfig   = true
//  config_output_path = var.output_kubeconfig
//  manage_aws_auth    = true
//
//  cluster_endpoint_private_access = true
//  cluster_endpoint_public_access  = true
//
//  worker_groups = [for wg in var.worker_groups: merge(wg, wg.autoscaling_enabled ? {
//    tags = [{
//      key                 = "k8s.io/cluster-autoscaler/enabled"
//      value               = "true"
//      propagate_at_launch = true
//    }, {
//      key                 = "k8s.io/cluster-autoscaler/${local.cluster_name}"
//      value               = local.cluster_name
//      propagate_at_launch = true
//    }]
//  } : {})]
//  map_users     = var.map_users
//
//  workers_additional_policies = concat(
//  [
//    "arn:aws:iam::aws:policy/IAMReadOnlyAccess",
//    "arn:aws:iam::aws:policy/AmazonS3FullAccess"
//  ],
//  (local.asg_enabled ? ["arn:aws:iam::aws:policy/AutoScalingFullAccess"] : [])
//  )
//
//  tags = {
//    Environment = var.environment_name
//  }
}
