package dam2.m6.pt2;

import java.util.List;

public class Autor {
	
	private String name;
	private String country;
	private int groupNumber;
	private String groupType;
	private List<Album> albums;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public List<Album> getAlbums() {
		return albums;
	}
	public void setAlbums(List<Album> songs) {
		this.albums = songs;
	}
	public int getGroupNumber() {
		return groupNumber;
	}
	public void setGroupNumber(int groupNumber) {
		this.groupNumber = groupNumber;
	}
	public String getGroupType() {
		return groupType;
	}
	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}
	
	public String toString() {
		String text;
		if(groupType.equals("Grup")) {
			text = String.format("Autor: %s (%s) - %s (%d components)\n", name, country, groupType, groupNumber);
		}else {
			text = String.format("Autor: %s (%s) - %s \n", name, country, groupType);
		}
		for(Album albumName : albums) {
			text += String.format("%d: %s\n",albumName.getYear(), albumName.getSongName());
		}
		text += "----------------------------------------------------";
		return text;
		
	}
}
