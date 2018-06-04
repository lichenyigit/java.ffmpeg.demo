package ffmpeg.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author lichenyi
 * @date 2018-6-3-0003.
 */
public class VideoToAudio {

    public static ConcurrentMap<String, Object> push() throws IOException {
        // 从map里面取数据，组装成命令
        String comm = "ffmpeg -iy e:\\laoliang.mp4 e:\\laoliang1.mp3";
        ConcurrentMap<String, Object> resultMap = null;
        // 执行命令行
        final Process proc = Runtime.getRuntime().exec(comm);
        System.out.println("执行命令----start commond");

        OutHandler errorGobbler = new OutHandler(proc.getErrorStream(), "Error");
        OutHandler outputGobbler = new OutHandler(proc.getInputStream(), "Info");

        errorGobbler.start();
        outputGobbler.start();
        // 返回参数
        resultMap = new ConcurrentHashMap<String, Object>();
        resultMap.put("info", outputGobbler);
        resultMap.put("error", errorGobbler);
        resultMap.put("process", proc);
        return resultMap;
    }

    public static void main(String args[]) {
        try {
            push();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
