package com.wjd.cmd;

import org.apache.commons.cli.*;

import java.util.Arrays;

public class Cmd {

    private final static Options options;

    static {
        options = new Options();
        options.addOption(new Option("help", false, "print help message"));
        options.addOption(new Option("?", false, "print help message"));
        options.addOption(new Option("version", false, "print version and exit"));
        options.addOption(new Option("verboseClassPath", false, "Displays path about class found."));
        options.addOption(new Option("verboseClass", false, "Displays information about each class loaded."));
        options.addOption(new Option("verboseInst", false, "Displays executed instructions."));
        options.addOption(Option.builder("Xjre")
                .hasArg().desc("jre directory")
                .type(String.class)
                .build());
        options.addOption(Option.builder("classpath")
                .hasArg().desc("classpath")
                .type(String.class)
                .build());
        options.addOption(Option.builder("cp")
                .hasArg().desc("classpath")
                .type(String.class)
                .build());
    }

    private String[] args;
    private boolean helpFlag = false;
    private boolean versionFlag = false;
    private boolean verboseClassPathFlag = false;
    private boolean verboseClassFlag = false;
    private boolean verboseInstFlag = false;
    private String mainClass;
    private String jreOption;
    private String cpOption;

    public static Cmd parseFrom(String[] args) {
        Cmd cmd = new Cmd();
        cmd.parse(args);
        return cmd;
    }

    /**
     * 解析命令行参数
     * @param args 命令行参数
     */
    public void parse(String[] args) {
        try {
            // 解析命令行参数
            CommandLineParser parser = new DefaultParser();
            CommandLine line = parser.parse(options, args);
            this.args = Arrays.copyOfRange(args, 1, args.length);

            // 打印帮助信息
            if (line.hasOption("help") || line.hasOption("?")) {
                helpFlag = true;
                printHelp();
            }

            // 版本信息
            if (line.hasOption("version")) {
                versionFlag = true;
                System.out.println("version 0.0.1");
            }

            // 打印类路径信息
            if (line.hasOption("verboseClassPath")) {
                verboseClassPathFlag = true;
            }

            // 打印类加载信息
            if (line.hasOption("verboseClass")) {
                verboseClassFlag = true;
            }

            // 打印指令执行信息
            if (line.hasOption("verboseInst")) {
                verboseInstFlag = true;
            }

            // 启动程序主类名
            if (args.length > 0) {
                mainClass = args[0];
            }

            // jre路径
            if (line.hasOption("Xjre")) {
                jreOption = line.getOptionValue("Xjre");
            }

            // classpath路径
            if (line.hasOption("classpath")) {
                cpOption = line.getOptionValue("classpath");
            } else if (line.hasOption("cp")) {
                cpOption = line.getOptionValue("cp");
            }

            // 打印所有参数
            System.out.println("helpFlag = " + helpFlag);
            System.out.println("versionFlag = " + versionFlag);
            System.out.println("verboseClassPathFlag = " + verboseClassPathFlag);
            System.out.println("verboseClassFlag = " + verboseClassFlag);
            System.out.println("verboseInstFlag = " + verboseInstFlag);
            System.out.println("mainClass = " + mainClass);
            System.out.println("jre = " + jreOption);
            System.out.println("classpath = " + cpOption);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * 打印命令行帮助信息
     */
    public static void printHelp() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("self-jvm", options);
    }

    public boolean isHelpFlag() {
        return helpFlag;
    }

    public boolean isVersionFlag() {
        return versionFlag;
    }

    public boolean isVerboseClassPathFlag() {
        return verboseClassPathFlag;
    }

    public boolean isVerboseClassFlag() {
        return verboseClassFlag;
    }

    public boolean isVerboseInstFlag() {
        return verboseInstFlag;
    }

    public String getJreOption() {
        return jreOption;
    }

    public String getCpOption() {
        return cpOption;
    }

    public String getMainClass() {
        return mainClass;
    }

    public String[] getArgs() {
        return args;
    }
}
