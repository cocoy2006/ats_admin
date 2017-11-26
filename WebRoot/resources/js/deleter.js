function del(msg, url) {
	var checked = $(":checked[name='ids']");
	if(checked.length <= 0) {
		return false;
	}
	if(confirm(msg)) {
		var ids = "";
		checked.each(function() {
    		ids += "ids=" + this.value + "&";
    	});
		$.ajax({
			url: url,
			data: ids,
			type: 'POST',
			dataType: 'json',
			success: function(data) {
				if(data == 1) {
					location.reload();
				}
			}
		});
	}
}