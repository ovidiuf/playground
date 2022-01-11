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

#
# Masters - Control Plane
#

resource "aws_instance" "master-node" {

  count                       = "${var.masters}"
  ami                         = "${var.ami_id}"
  instance_type               = "${var.master_instance_type}"
  subnet_id                   = "${var.subnet_id}"
  key_name                    = "${var.ssh_key_name}"
  security_groups             = "${var.security_groups}"
  associate_public_ip_address = false

  tags = {

    Name = "kubernetes-master-${count.index + 1}"
  }
}

#
# Workers - Data Plane
#

resource "aws_instance" "worker-node" {

  count                       = "${var.workers}"
  ami                         = "${var.ami_id}"
  instance_type               = "${var.worker_instance_type}"
  subnet_id                   = "${var.subnet_id}"
  key_name                    = "${var.ssh_key_name}"
  security_groups             = "${var.security_groups}"
  associate_public_ip_address = false

  tags = {

    Name = "kubernetes-worker-${count.index + 1}"
  }
}

#
# Optionally, call a sub-module
#
