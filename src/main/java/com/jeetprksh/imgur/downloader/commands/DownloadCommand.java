package com.jeetprksh.imgur.downloader.commands;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class DownloadCommand {

	@ShellMethod("Say hello.")
    public String greet(@ShellOption(defaultValue="World") String who) {
            return "Hello " + who;
    }
	
}
