package com.xiaomo.db.model;

public class CarIllegalInfo {



	public Integer _id;
	public String carNumber;
	public String type;
	public String illegalId;
	public String illegalInfo;
	public String address;
	public String img1;
    public String img2;
	public Integer isReported;
	public Integer serverCarId;
    public String createTime;

    /*
    + "  _id integer  primary key,
+ "  car_number varchar(20) ,"
+ "  type varchar(50) ,"
+ "  illegal_id varchar(50) ,"
+ "  illegal_info varchar(50)
+ "  address varchar(500)   ,"
+ "  img1 varchar(50)  ,"
+ "   img2 varchar(50)  ,"
+ "   is_reported smallint  ,"
+ "  server_carid integer ,"
+ "  create_time varchar(50) "

     */

    public CarIllegalInfo(Integer _id, String carNumber, String type, String illegalId, String illegalInfo,
                          String address, String img1, String img2 , Integer isReported, Integer serverCarId , String createTime) {
        this._id = _id;
        this.carNumber = carNumber;
        this.type = type;
        this.illegalId = illegalId;
        this.illegalInfo = illegalInfo;
        this.address = address;
        this.img1 = img1;
        this.img2 = img2;
        this.createTime = createTime;
        this.isReported = isReported;
        this.serverCarId = serverCarId;
    }

    public CarIllegalInfo() {
	}

}
