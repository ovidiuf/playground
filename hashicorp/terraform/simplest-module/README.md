# Simplest Terraform Module

## Overview

This is the simplest, prototypical Terraform module that can be used for experimentation and
as a base for developing more complex modules. It does not have dependencies. It has a simple
set of input variables, declared conventionally in the `variables.tf` file and a simple set of 
outputs, declared in `outputs.tf`. The resources are declared as usual in `main.tf`

The module deploys a simple EC2 VM in the default AWS account. The VM does not have a public IP
so it can only be accessed by connecting to other VMs in the same private subnet.

Some aspects, such as the name of the SSH key pair that will be used to secure access,
can be configured. Defaults are provided in `variables.tf`.

## NOKB

https://kb.novaordis.com/index.php/Writing_a_Terraform_Module#Playground

## Operations

### Install

### Uninstall 