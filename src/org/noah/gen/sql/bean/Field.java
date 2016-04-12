package org.noah.gen.sql.bean;

/**
 * @author noah.yang
 *
 */
public class Field {
	private int index;
	private FieldType type;
	public Field() {
		this.index = 0;
		this.type = FieldType.kFieldTypeNum;
	}
	public Field(int index, FieldType type) {
		this.index = index;
		this.type = type;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public FieldType getType() {
		return type;
	}
	public void setType(FieldType type) {
		this.type = type;
	}
}
