package br.com.maxtercreations.runmanager.running.export;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.json.simple.JSONObject;

import br.com.maxtercreations.runmanager.constants.Constants;
import br.com.maxtercreations.runmanager.database.RunnerUtils;
import br.com.maxtercreations.runmanager.displays.running.RankingDisplay;
import br.com.maxtercreations.runmanager.loader.Loader;
import br.com.maxtercreations.runmanager.logger.Logger;
import br.com.maxtercreations.runmanager.manager.Manager;
import br.com.maxtercreations.runmanager.utilitaries.callback.Callback;

public class Export {

	private Manager manager = Loader.getManager();

	private boolean exporting;
	private int rownum = 0;
	
	private ArrayList<RunnerUtils> officialRunners = new ArrayList<>(),  unofficialRunners = new ArrayList<>();

	private void build(Workbook workbook, boolean official) {

		rownum = 0;
		
		Sheet sheet = workbook.createSheet("Percurso " + (official ? "Oficial" : "Não Oficial (5 KM)"));

		CellStyle centerStyle = workbook.createCellStyle();
		centerStyle.setAlignment(CellStyle.ALIGN_CENTER);

		Row header = sheet.createRow(rownum++);

		Cell posCell = header.createCell(0);
		posCell.setCellValue("Colocação");
		posCell.setCellStyle(centerStyle);

		Cell nameCell = header.createCell(1);
		nameCell.setCellValue("Nome");
		nameCell.setCellStyle(centerStyle);

		Cell timeCell = header.createCell(2);
		timeCell.setCellValue("Tempo");
		timeCell.setCellStyle(centerStyle);

		Cell barcodeCell = header.createCell(3);
		barcodeCell.setCellValue("Código");
		barcodeCell.setCellStyle(centerStyle);

		Map<RunnerUtils, Long> ranking = RankingDisplay.doneMap(official ? officialRunners : unofficialRunners);

		int pos = 1;

		for (RunnerUtils runner : ranking.keySet()) {

			int cellnum = 0;

			if (ranking.get(runner) <= 0)
				continue;
			
			String time = manager.getUtils().convertTime(ranking.get(runner));

			String name = runner.getName();
			String barcode = runner.getBarcode();

			Row row = sheet.createRow(rownum++);

			Cell posC = row.createCell(cellnum++);
			posC.setCellValue((pos++) + "º");
			posC.setCellStyle(centerStyle);

			Cell nameC = row.createCell(cellnum++);
			nameC.setCellValue(name);
			nameC.setCellStyle(centerStyle);

			Cell timeC = row.createCell(cellnum++);
			timeC.setCellValue(time + "s");
			timeC.setCellStyle(centerStyle);

			Cell barcodeC = row.createCell(cellnum++);
			barcodeC.setCellValue(barcode);
			barcodeC.setCellStyle(centerStyle);

		}

		rownum++;

		sheet.autoSizeColumn(1);
		sheet.autoSizeColumn(2);
		sheet.autoSizeColumn(3);
	}

	public void export(String email) {
		exporting = true;
		
		HSSFWorkbook workbook = new HSSFWorkbook();

		@SuppressWarnings("unchecked")
		ArrayList<String> keys = new ArrayList<>(((JSONObject) manager.getRunnerManager().getRunners().get(0)).keySet());
	
		for (String domainId : keys) {
			RunnerUtils runner = new RunnerUtils(domainId);
		
			if (runner.isInOfficalRunning()) {
				officialRunners.add(runner);
			} else {
				unofficialRunners.add(runner);
			}
		}
		
		build(workbook, true);
		build(workbook, false);
		
		try {
			File file = new File(Constants.BACKUP_FOLDER + "/Relatório da Corrida (" + new SimpleDateFormat("dd-MM-yyyy HH-mm-ss").format(new Date()) + ").xls");
			FileOutputStream out = new FileOutputStream(file);
			workbook.write(out);
			out.close();

			Email.send(email, file, new Callback() {
				@Override
				public void done() {
					System.out.println("ERRO");
				}
			});
			Logger.log("Dados de corredores exportados com sucesso!");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isAlreadyExporting() {
		return exporting;
	}

	public void setExporting(boolean exporting) {
		this.exporting = exporting;
	}

}