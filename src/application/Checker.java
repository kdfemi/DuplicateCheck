package application;

import java.io.File;
import java.util.HashSet;
import java.util.regex.Pattern;

public class Checker {

	private String path1;
	private String path2;
	private final String regex = "([a-zA-Z]:)?(\\\\[a-zA-Z0-9_-]+)+\\\\?";
	
	public Checker(String _path1, String _path2) {
		this.setPath1(_path1);
		this.setPath2(_path2);
	}
	
	public String getPath1() {
		return path1;
	}
	public void setPath1(String path1) {
		this.path1 = path1;
	}
	public String getPath2() {
		return path2;
	}
	public void setPath2(String path2) {
		this.path2 = path2;
	}
	
	public final Boolean comparePath() {
		if(Pattern.matches(regex, this.path1) && Pattern.matches(regex, this.path2))
			return true;
		return false;
	}
	
	public HashSet<String>loadFiles() throws Exception{
		File folder1 = new File(this.path1);
		File folder2 = new File(this.path2);
		if(comparePath()&&folder1.isDirectory()&&folder2.isDirectory()) {
			File [] pathFile1 = folder1.listFiles();
			File [] pathFile2 = folder2.listFiles();
			HashSet  <String>duplicates = new HashSet<>();
			HashSet <String>pathFileName1 = new HashSet<>();
			HashSet <String>pathFileName2 = new HashSet<>();
			
			Thread t1 = new Thread(new Runnable() {

				@Override
				public void run() {
				
					for(File file:pathFile1) {
						
						pathFileName1.add(file.getName());
						System.out.println(file.getName() + " " +this.getClass().getName());
					}
				}
			});

			Thread t2 = new Thread(new Runnable() {

				@Override
				public void run() {
					for(File file:pathFile2) {
						
						pathFileName2.add(file.getName());
						System.out.println(file.getName() + " " +this.getClass().getName());
					}
				}
			});
			t1.start();
			t2.start();
			t1.join(0);
			t2.join(0);
			
			duplicates.addAll(pathFileName1);
			duplicates.removeAll(pathFileName2);
			return duplicates;
		}
		return new HashSet<String>();
	}
}
