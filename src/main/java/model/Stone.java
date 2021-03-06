package model;

import interfaces.IId;
import interfaces.IRateable;

import org.apache.commons.lang.StringUtils;
import org.jsefa.csv.annotation.CsvDataType;
import org.jsefa.csv.annotation.CsvField;
import org.jsefa.xml.annotation.XmlDataType;
import org.jsefa.xml.annotation.XmlElement;

@XmlDataType(defaultElementName = "stone")
@CsvDataType()
public class Stone implements IRateable, IId{

	@CsvField(pos = 1)
	@XmlElement(name = "InvNr1", pos = 1)
	String InvNr1;

	@CsvField(pos = 2)
	@XmlElement(name = "MusId", pos = 2)
	String MusId;

	@CsvField(pos = 3)
	@XmlElement(name = "Link", pos = 3)
	String Link;

	@CsvField(pos = 4)
	@XmlElement(name = "InvNr2", pos = 4)
	String InvNr2;

	@CsvField(pos = 5)
	@XmlElement(name = "Bild", pos = 5)
	String Bild;

	@CsvField(pos = 6)
	@XmlElement(name = "Titel", pos = 6)
	String Titel;

	@CsvField(pos = 7)
	@XmlElement(name = "Masze", pos = 7)
	String Masze;

	@CsvField(pos = 8)
	@XmlElement(name = "Hoehe", pos = 8)
	String Hoehe;

	@CsvField(pos = 9)
	@XmlElement(name = "Breite", pos = 9)
	String Breite;

	@CsvField(pos = 10)
	@XmlElement(name = "Tiefe", pos = 10)
	String Tiefe;

	@CsvField(pos = 11)
	@XmlElement(name = "Unit", pos = 11)
	String Unit;

	@CsvField(pos = 12)
	@XmlElement(name = "Material", pos = 12)
	String Material;

	@CsvField(pos = 13)
	@XmlElement(name = "MaterialId", pos = 13)
	String MaterialId;

	@CsvField(pos = 14)
	@XmlElement(name = "Datierung", pos = 14)
	String Datierung;

	@CsvField(pos = 15)
	@XmlElement(name = "Erdzeitalter", pos = 15)
	String Erdzeitalter;

	@CsvField(pos = 16)
	@XmlElement(name = "ErdzeitalterId", pos = 16)
	String ErdzeitalterId;

	@CsvField(pos = 17)
	@XmlElement(name = "Herkunft", pos = 17)
	String Herkunft;

	@CsvField(pos = 18)
	@XmlElement(name = "HerkunftCoord", pos = 18)
	String HerkunftCoord;

	@CsvField(pos = 19)
	@XmlElement(name = "HerkunftId", pos = 19)
	String HerkunftId;

	@CsvField(pos = 20)
	@XmlElement(name = "HerkunftGeoId", pos = 20)
	String HerkunftGeoId;

	@CsvField(pos = 21)
	@XmlElement(name = "Fundort", pos = 21)
	String Fundort;

	@CsvField(pos = 22)
	@XmlElement(name = "FundortCoord", pos = 22)
	String FundortCoord;

	@CsvField(pos = 23)
	@XmlElement(name = "FundortId", pos = 23)
	String FundortId;

	@CsvField(pos = 24)
	@XmlElement(name = "FundortGeoId", pos = 24)
	String FundortGeoId;

	@CsvField(pos = 25)
	@XmlElement(name = "Kommentar", pos = 25)
	String Kommentar;

	@XmlElement(name = "Id", pos = 26)
	Integer id;

	// rating brauch keine Annotations
	Double rating;

	public Integer getId() {
		return this.id;
	}

	public Double getRating() {
		return this.rating;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public static Stone create(String[] list) {
		Stone stone = new Stone();
		stone.InvNr1 = list[0];
		stone.MusId = list[1];
		stone.Link = list[2];
		stone.InvNr2 = list[3];
		stone.Bild = list[4];
		stone.Titel = list[5];
		stone.Masze = list[6];
		stone.Hoehe = list[7];
		stone.Breite = list[8];
		stone.Tiefe = list[9];
		stone.Unit = list[10];
		stone.Material = list[11];
		stone.MaterialId = list[12];
		stone.Datierung = list[13];
		stone.Erdzeitalter = list[14];
		stone.ErdzeitalterId = list[15];
		stone.Herkunft = list[16];
		stone.HerkunftCoord = list[17];
		stone.HerkunftId = list[18];
		stone.HerkunftGeoId = list[19];
		stone.Fundort = list[20];
		stone.FundortCoord = list[21];
		stone.FundortId = list[22];
		stone.FundortGeoId = list[23];
		stone.Kommentar = list[24];
		return stone;
	}

	public String toString() {
		return "ID: " + this.id + ", Link: " + this.Link;
	}
	
	

	public String getInvNr1() {
		return InvNr1;
	}

	public String getMusId() {
		return MusId;
	}

	public String getLink() {
		return Link;
	}

	public String getInvNr2() {
		return InvNr2;
	}

	public String getBild() {
		return Bild;
	}

	public String getTitel() {
		return Titel;
	}

	public String getMasze() {
		return Masze;
	}

	public String getHoehe() {
		return Hoehe;
	}

	public String getBreite() {
		return Breite;
	}

	public String getTiefe() {
		return Tiefe;
	}

	public String getUnit() {
		return Unit;
	}

	public String getMaterial() {
		return Material;
	}

	public String getMaterialId() {
		return MaterialId;
	}

	public String getDatierung() {
		return Datierung;
	}

	public String getErdzeitalter() {
		return Erdzeitalter;
	}

	public String getErdzeitalterId() {
		return ErdzeitalterId;
	}

	public String getHerkunft() {
		return Herkunft;
	}

	public String getHerkunftCoord() {
		return HerkunftCoord;
	}

	public String getHerkunftId() {
		return HerkunftId;
	}

	public String getHerkunftGeoId() {
		return HerkunftGeoId;
	}

	public String getFundort() {
		return Fundort;
	}

	public String getFundortCoord() {
		return FundortCoord;
	}

	public String getFundortId() {
		return FundortId;
	}

	public String getFundortGeoId() {
		return FundortGeoId;
	}

	public String getKommentar() {
		return Kommentar;
	}
	
	public Coordinates getCoordinates(){
		if(!StringUtils.isBlank(this.FundortCoord)){
			String[] coord = this.FundortCoord.split(",");
			return new Coordinates(Double.valueOf(coord[0].trim()), Double.valueOf(coord[1].trim()));
		}
		return null;
	}

	public Stone() {
	}

	/**
	 * Dieser Konstruktor dient nur zu Testzwecken.
	 * 
	 * @param id
	 * @param titel
	 */
	public Stone(Integer id, String titel) {
		this.id = id;
		this.Titel = titel;
	}

}
