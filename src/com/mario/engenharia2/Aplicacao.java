/*
 * Desenvolvido por Mario de Carvalho Joaquim Filho
 * Matricula 201020321 - UFLA
 * 
 */

package com.mario.engenharia2;

import java.io.File;
import java.io.FileFilter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class Aplicacao {

	private static List<SoftwareAnalisado> softwares;

	public static void analisaXML(File arq) {
		try {
			SAXBuilder builder = new SAXBuilder();
			Document doc = builder.build(arq);
			Element root = doc.getRootElement();

			List<Element> metricas = root.getChildren();

			Iterator<Element> i = metricas.iterator();
			SoftwareAnalisado sw = new SoftwareAnalisado(root.getAttributeValue("scope"));

			if (arq.getName().contains("C1")) {
				sw.setColeta(1);
			} else if (arq.getName().contains("C2")) {
				sw.setColeta(2);
			} else if (arq.getName().contains("C3")) {
				sw.setColeta(3);
			} else if (arq.getName().contains("C4")) {
				sw.setColeta(4);
			}

			while (i.hasNext()) {
				Element elemento = (Element) i.next();
				String id = elemento.getAttributeValue("id");
				String descricao = elemento.getAttributeValue("description");
				String tipo = elemento.getChildren().get(0).getAttributeValue("per");
				String valorString = elemento.getChildren().get(0).getAttributeValue("avg");
				String maxString = elemento.getChildren().get(0).getAttributeValue("max");
				String stddevString = elemento.getChildren().get(0).getAttributeValue("stddev");

				Double valorMedio = null;
				Double max = null;
				Double stddev = null;

				if (maxString != null) {
					maxString = maxString.replaceAll(",", ".");
					max = Double.parseDouble(maxString);
				}
				if (stddevString != null) {
					stddevString = stddevString.replaceAll(",", ".");
					stddev = Double.parseDouble(stddevString);
				}
				if (valorString != null) {
					valorString = valorString.replaceAll(",", ".");
					valorMedio = Double.parseDouble(valorString);
				}

				Metrica mt = new Metrica(id, descricao, tipo, valorMedio, max, stddev);
				sw.addMetrica(mt);
			}

			softwares.add(sw);
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void exportaCSV() {
		StringBuffer str = new StringBuffer();
		for (SoftwareAnalisado s : softwares) {
			// Arquivo texto resultante do processamento do // XML gerado pelo
			// plugin Metrics
			// CAMPOS:
			// [nome da métrica]; [avg];[stddev];[max]
			// onde:
			// [nome da métrica] -> nome da métrica avaliada
			// [avg]-> média do valor calculado para métrica;
			// [stddev]-> desvio padrão da média;
			// [max]-> maior valor encontrado para a métrica

			str.append("Software Analisado: " + s.getNome() + "\n");
			str.append("Coleta: " + s.getColeta() + "\n");
			str.append("[nome da métrica];[avg];[stddev];[max]\n");
			for (Metrica m : s.getMetricas()) {
				str.append(m.getDescricao() + ";");
				str.append(m.getValorMedio() + ";");
				str.append(m.getStddev() + ";");
				str.append(m.getMax() + ";");
				str.append("\n");
			}
			str.append("\n\n");
		}
		try {
			File arquivo = new File("saida.csv");
			FileWriter out = new FileWriter(arquivo);
			PrintWriter writer = new PrintWriter(out);
			writer.print(str.toString());
			writer.close();
			out.close();

		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Erro ao ler o arquivo. \n" + e.getMessage(), "ERRO",
					JOptionPane.ERROR_MESSAGE);
		}

	}

	public static void main(String[] args) {

		softwares = new ArrayList<SoftwareAnalisado>();

		FileFilter filter = new FileFilter() {
			public boolean accept(File file) {
				return file.getName().endsWith(".xml");
			}
		};
		File dir = new File(".");
		JFileChooser fileChooser = new JFileChooser(dir);
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			dir = fileChooser.getSelectedFile();
			File[] files = dir.listFiles(filter);
			for (File f : files) {
				analisaXML(f);
			}
			

			for (SoftwareAnalisado s : softwares) {
				System.out.println("Software Analisado: " + s.getNome());
				System.out.println("Coleta: " + s.getColeta());
				System.out.println("Metricas: ");
				for (Metrica m : s.getMetricas()) {
					System.out.println("ID: " + m.getId());
					System.out.println("Descricao: " + m.getDescricao());
					System.out.println("Tipo: " + m.getTipo());
					System.out.println("Valor medio: " + m.getValorMedio());
					System.out.println("Max: " + m.getMax());
					System.out.println("Stddev: " + m.getStddev());
					System.out.println();
				}
			}
			
			exportaCSV();
		}else{
			System.out.println("Nada selecionado");
		}

	}

}
