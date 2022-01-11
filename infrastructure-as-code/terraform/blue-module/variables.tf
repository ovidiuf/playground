variable "masters" {
  description = "The number of Kuberntes master nodes to be created. For HA, at least 3 should be created."
  type        = number
  default     = 1
}

variable "master_instance_type" {
  description = "Kubernetes master Amazon instance type."
  type        = string
  default     = "t2.small"
}

variable "workers" {
  description = "The number of Kuberntes worker nodes to be created."
  type        = number
  default     = 2
}

variable "worker_instance_type" {
  description = "Kubernetes worker Amazon instance type."
  type        = string
  default     = "t2.small"
}

variable "ami_id" {
  description = "The ID of the AMI to build the nodes from."
  type        = string
  default     = "ami-06faba49dddaecfb6"
}

variable "subnet_id" {
  description = "The subnet ID."
  type        = string
  default     = "subnet-0fe2965e8bf239c09"
}

variable "ssh_key_name" {
  description = "The name of an EC2 Key Pair that can be used to SSH to the EC2 Instances in this cluster. Set to an empty string to not associate a Key Pair."
  type        = string
  default     = "sbox-ml-kp-01"
}

variable "security_groups" {
  description = "The list of security groups"
  type        = list
  default     = ["sg-041f9d51704199e98"]
}

