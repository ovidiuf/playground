provider "aws" {

  profile = "default"
  region  = "us-west-2"
}

resource "aws_instance" "terraform-experiment-01" {

  ami                         = "ami-06faba49dddaecfb6"
  instance_type               = "t2.small"
  subnet_id                   = "subnet-0fe2965e8bf239c09"
  key_name                    = "sbox-ml-kp-01"
  security_groups             = ["sg-041f9d51704199e98"]
  associate_public_ip_address = false

  tags = {

    Name = "terraform-experiment-01"
  }
}