task<Exec>("composeDown") {
    commandLine("docker", "compose", "down")
}

extra["composeFileDir"] = layout.projectDirectory