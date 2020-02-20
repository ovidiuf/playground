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

#
# Declare resources here
#

resource "aws_instance" "ec2-instance" {

  count                       = 1
  ami                         = var.ami_id
  instance_type               = var.instance_type
  ssh_key_pair_name           = var.ssh_key_pair_name
  subnet_id                   = var.subnet_id
  security_group_ids          = var.security_group_ids
  associate_public_ip_address = false

  tags = {
    Name = "simplest"
  }
}