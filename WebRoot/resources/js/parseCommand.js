function parseCommand(action, param) {
	var params = null;
	switch(action) {
		case "tap":
			params = param.split(" ");
			command = "点击屏幕:(" + params[0] + "," + params[1] + ")";
			break;
		case "swipe":
			if(param.indexOf(" ") > -1) {
				params = param.split(" ");
				command = "滑动屏幕:(" + params[0] + "," + params[1] + ")到(" + params[2] + "," + params[3] + ")";
			} else {
				switch(param) {
					case "left":
						command = "左滑";
						break;
					case "right":
						command = "右滑";
						break;
					case "up":
						command = "上滑";
						break;
					case "down":
						command = "下滑";
						break;
					default:
						break;
				}
			}
			break;
		case "keyevent":
			command = "点击:";
			switch(param) {
				case 3: case "3":
					command += "主页键";
					break;
				case 4: case "4":
					command += "返回键";
					break;
				case 82: case "82":
					command += "菜单键";
					break;
				default:
					break;
			}
			break;
		case "text":
			command = "输入:" + param;
			break;
		default:
			break;
	}
	return command;
}