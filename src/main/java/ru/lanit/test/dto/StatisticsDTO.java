package ru.lanit.test.dto;

public class StatisticsDTO {
	private long personcount;
	private long carcount;
	private long uniquevendorcount;

	public StatisticsDTO(long personcount, long carcount, long uniquevendorcount) {
		this.personcount = personcount;
		this.carcount = carcount;
		this.uniquevendorcount = uniquevendorcount;
	}

	public long getPersoncount() {
		return personcount;
	}

	public void setPersoncount(long personcount) {
		this.personcount = personcount;
	}

	public long getCarcount() {
		return carcount;
	}

	public void setCarcount(long carcount) {
		this.carcount = carcount;
	}

	public long getUniquevendorcount() {
		return uniquevendorcount;
	}

	public void setUniquevendorcount(long uniquevendorcount) {
		this.uniquevendorcount = uniquevendorcount;
	}

}
