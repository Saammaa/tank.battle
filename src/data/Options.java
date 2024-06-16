package data;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class Options {
	private final Properties props;

	// 配置文件的名称。其应直接保存于工作根目录下
	private final String configFilePath = "config.ini";

	public Options() {
		props = new Properties();
		loadConfig();
	}

	private void loadConfig() {
		try (InputStream inputStream = Files.newInputStream(
				Paths.get(configFilePath)
		)) {
			props.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getOption(String key) {
		return this.props.getProperty(key);
	}

	public int getIntOption(String key) {
		String value = this.getOption(key);
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public boolean getBoolOption(String key) {
		return this.getIntOption(key) == 1;
	}

	public double getDoubleOption(String key) {
		String value = this.getOption(key);
		try {
			return Double.parseDouble(value);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return 0.0;
		}
	}

	public void setOption(String key, String value) {
		this.props.setProperty(key, value);
		this.saveConfig();
	}

	private void saveConfig() {
		try (OutputStream outputStream = Files.newOutputStream(
				Paths.get(configFilePath)
		)) {
			props.store(outputStream, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}