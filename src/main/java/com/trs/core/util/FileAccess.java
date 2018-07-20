package com.trs.core.util;

import java.io.*;

public class FileAccess implements Serializable {

	public FileAccess() throws IOException {
		this("", 0L);
	}

	public FileAccess(String sName, long nPos) throws IOException {
		oSavedFile = new RandomAccessFile(sName, "rw");
		this.nPos = nPos;
		oSavedFile.seek(nPos);
	}

	public synchronized int write(byte b[], int nStart, int nLen) {
		int n = -1;
		try {
			oSavedFile.write(b, nStart, nLen);
			n = nLen;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return n;
	}

	public synchronized void close() {
		try {
			oSavedFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public synchronized int read(byte b[], int nStart, int nLen) {
		int n = -1;
		try {
			oSavedFile.read(b, nStart, nLen);
			n = nLen;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return n;
	}

	private static final long serialVersionUID = -2850423595470543122L;
	RandomAccessFile oSavedFile;
	long nPos;
}