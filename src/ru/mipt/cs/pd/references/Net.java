package ru.mipt.cs.pd.references;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

public class Net {

	public String getListOfSpecies(String sp) throws ClientProtocolException,
			IOException {
		String url = new String(
				"http://www.kazusa.or.jp/codon/cgi-bin/spsearch.cgi?species="
						+ sp + "&c=s");
		
		// create connection
		HttpClient client = HttpClientBuilder.create().build();

		// create a query for the resource
		HttpGet request = new HttpGet(url);

		// execute the query
		HttpResponse response = client.execute(request);

		// Построчно считываем полученную страницу
		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
		result.append(line);
		}

		List<String> names = new ArrayList<String>();
		List<String> urls = new ArrayList<String>();
		final String patternRef = "<a href=\"/codon/cgi-bin/showcodon.cgi?species=";
		final String patternRefEnd = "\"";
		final String patternInf = "<i>";
		final String patternInfEnd = "</i>";
		int startIndex = 0;
		String lowerCaseResult = result.toString().toLowerCase();
		while (true) {
			int refIndex = lowerCaseResult.indexOf(patternRef, startIndex);
			if (refIndex == -1)
				break;
			refIndex += patternRef.length();
			int refEnd = lowerCaseResult.indexOf(patternRefEnd, refIndex);
			String index = result.substring(refIndex, refEnd);
			refIndex = lowerCaseResult.indexOf(patternInf, refEnd);
			refIndex += patternInf.length();
			refEnd = lowerCaseResult.indexOf(patternInfEnd, refIndex);
			String description = result.substring(refIndex, refEnd);
			names.add(description);
			urls.add(index);
			startIndex = refEnd;
		}
		String list = new String("");
		for (int i = 0; i < names.size(); i++) {
			list = list + names.get(i) + ", index = " + urls.get(i) + "\n";
		}
		return list;
	}

	public void getTable1(String index) throws ClientProtocolException,
			IOException {
		String url = "http://www.kazusa.or.jp/codon/cgi-bin/showcodon.cgi?species="
				+ index;
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(url);
		request.addHeader("User-Agent", "Apache-HttpClient/4.3.3 (java 1.5)");
		HttpResponse response = client.execute(request);

		BufferedReader rd = new BufferedReader(new InputStreamReader(response
				.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}

		final String beg = "<PRE>";
		final String end = "</PRE>";
		String res = result.substring(result.indexOf(beg) + 5,
				result.indexOf(end));
		//System.out.println(res);
		String table = new String("");
		int i;

		for (int n = 0; n < 8; n = n + 1) {
			for (i = 4 * n + 1; i < 4 * (n + 1) + 1; i = i + 1) {
				table = table
						+ "\n"
						+ res.substring((i - 1) * 18 - 2 * n, i * 18 - 2
								* (n + 1));
			}
		}

		PrintWriter out = new PrintWriter(new File("file1").getAbsoluteFile());
		try {
			out.print(table);
		} finally {
			out.close();
		}
		// return table;

	}

	public void getTable2(String index) throws ClientProtocolException,
			IOException {
		String url = "http://www.kazusa.or.jp/codon/cgi-bin/showcodon.cgi?species="
				+ index;
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(url);
		request.addHeader("User-Agent", "Apache-HttpClient/4.3.3 (java 1.5)");
		HttpResponse response = client.execute(request);

		BufferedReader rd = new BufferedReader(new InputStreamReader(response
				.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}

		final String beg = "<PRE>";
		final String end = "</PRE>";
		String res = result.substring(result.indexOf(beg) + 5,
				result.indexOf(end));
		// System.out.println(res);
		String table = new String("");
		int i;
		for (int n = 8; n < 16; n = n + 1) {
			for (i = 4 * n + 1; i < 4 * (n + 1) + 1; i = i + 1) {
				table = table
						+ "\n"
						+ res.substring((i - 1) * 18 - 2 * n, i * 18 - 2
								* (n + 1));
			}
		}

		PrintWriter out = new PrintWriter(new File("file2").getAbsoluteFile());
		try {
			out.print(table);
		} finally {
			out.close();
		}
		// return table;

	}
}
