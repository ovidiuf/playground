# Simplest Terraform Module

## Overview

This is the simplest, prototypical Terraform module that can be used for experimentation and
as a base for developing more complex modules. It does not have dependencies. It has a simple
set of input variables, declared conventionally in the `variables.tf` file and a simple set of 
outputs, declared in `outputs.tf`. The resources are declared as usual in `main.tf`

The module deploys a simple EC2 VM in the default AWS account.

Some aspects of the VM can be configured.

## Operations

### Install

### Uninstall 