package com.oj.entity.other;
/**
 * @author zt
 * @Time 2019年5月7日 10点53分
 * @Description 对应文件存储路径类
 */
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FilePath {
    @Value("${spring.fileUploadPath.WinPath}")
    private static String WinPath;
    @Value("${spring.fileUploadPath.LinuxPath}")
    private static String LinuxPath;

    public static String getWinPath() {
        return WinPath;
    }

    @Value("${spring.fileUploadPath.WinPath}")
    public void setWinPath(String WinPath) {
        this.WinPath = WinPath;
    }

    public static String getLinuxPath() {
        return LinuxPath;
    }

    @Value("${spring.fileUploadPath.LinuxPath}")
    public void setLinuxPath(String LinuxPath) {
        this.LinuxPath = LinuxPath;
    }

    public String toString()
    {
        return "yml win path is " + WinPath + "  yml linux path is " + LinuxPath;
    }

}
