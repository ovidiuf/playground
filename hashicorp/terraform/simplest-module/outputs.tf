output "private_ip" {
  value = aws_instance.ec2-instance.private_ip
  description = "The private IP address of the instance"
}