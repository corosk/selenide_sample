package org.selenide.examples;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class CsvFileService {
    public static List<RedirectBeans> csvReader(final String file) {
	List<RedirectBeans> urlList = new ArrayList<>();
	try (BufferedReader br = new BufferedReader(new FileReader(new File(file)))) {
	    String line;
	    // 1行ずつCSVファイルを読み込む
	    while ((line = br.readLine()) != null) {
		String[] data = line.split(","); // 行をカンマ区切りで配列に変換
		RedirectBeans bean = new RedirectBeans();
		bean.originUrl = data[0];
		bean.redirectUrl = data[1];
		urlList.add(bean);
	    }
	} catch (IOException e) {
	    System.out.println(e);
	}
	return urlList;
    }

    public static void csv_writer(List<String> result, String output) {
	try (FileWriter fw = new FileWriter(output, false); PrintWriter pw = new PrintWriter(new BufferedWriter(fw))) {
	    for (String res : result) {
		pw.print(res);
		pw.println();
	    }
	    // 終了メッセージを画面に出力する
	    System.out.println("出力が完了しました。");
	} catch (IOException ex) {
	    // 例外時処理
	    ex.printStackTrace();
	}
    }
}
