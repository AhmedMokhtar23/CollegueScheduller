package csc.colleguescheduller.Models.Schedule.SchemaSchedule;

import org.threeten.bp.DayOfWeek;

import java.util.ArrayList;
import java.util.HashMap;

import csc.colleguescheduller.Models.Schedule.Schedule;
import csc.colleguescheduller.Models.Schedule.Schema.Schema;

public class SchemaSchedule extends Schedule {

    private Schema schema;
    private HashMap<DayOfWeek, ArrayList<SchemaCell>> rows;

    public SchemaSchedule() {
        super();
        this.rows = new HashMap<>();
    }
    
    public SchemaSchedule(int workDays, DayOfWeek startDay) {
        super(workDays, startDay);
        this.rows = new HashMap<>();
    }
              
    public SchemaSchedule(HashMap<String, Object> data, Schema schema) {
        super(5, DayOfWeek.SUNDAY);
        this.schema = schema;
        this.rows = (HashMap<DayOfWeek, ArrayList<SchemaCell>>) data.get("Rows");
    }
     
	public Schema getSchema() {
		return this.schema;
	}

	public void setSchema(Schema schema) {
		this.schema = schema;
	}

	public HashMap<DayOfWeek, ArrayList<SchemaCell>> getRows() {
		return this.rows;
	}

	public void setRows(HashMap<DayOfWeek, ArrayList<SchemaCell>> rows) {
		this.rows = rows;
	}

    public HashMap<String ,Object> to_HashMap(){
        HashMap<String, Object> result = new HashMap<String, Object>();
        
        result.put("Work Days", this.getWorkDays());
        result.put("Start Day", this.getStartDay());
        result.put("Schema", this.schema.getSchemaID());
        result.put("Rows", this.rows);

        return result;
    }

}
