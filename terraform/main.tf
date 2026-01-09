terraform {
  required_providers {
    docker = {
      source  = "kreuzwerker/docker"
    }
  }
}

provider "docker" {
  host = "unix:///var/run/docker.sock" # Use "npipe:////./pipe/docker_engine" if on Windows
}

# 1. Create a Network for the Build Tools
resource "docker_network" "jenkins_network" {
  name = "jenkins_network"
}

# 2. Create the Volume (So Jenkins doesn't lose data when restarted)
resource "docker_volume" "jenkins_data" {
  name = "jenkins_data"
}

resource "docker_image" "jenkins" {
  name         = "jenkins/jenkins:lts-jdk17"
  keep_locally = true
}

resource "docker_container" "jenkins_server" {
  name  = "jenkins_server"
  image = docker_image.jenkins.image_id

  ports {
    internal = 8080
    external = 8088 # We use 8088 to avoid conflict with your apps
  }

  ports {
    internal = 50000
    external = 50000
  }

  networks_advanced {
    name = docker_network.jenkins_network.name
  }

  volumes {
    container_path = "/var/jenkins_home"
    volume_name    = docker_volume.jenkins_data.name
  }

  # THE MAGIC SAUCE: Mounting the host's Docker socket
  # This lets Jenkins run "docker build" commands using YOUR laptop's Docker engine
  volumes {
    host_path      = "/var/run/docker.sock" # Windows: "//./pipe/docker_engine" (requires tricks) or "/var/run/docker.sock" in WSL2
    container_path = "/var/run/docker.sock"
  }

  # Allow Jenkins to read the socket (User 0 is root - insecure but necessary for local Docker-in-Docker)
  user = "root"
}