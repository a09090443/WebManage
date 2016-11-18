<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/jsp/head.jsp"%>
<title>jqGrid Loading Data - Dialogs - Edit, Add, Delete</title>
</head>
<style media="all" type="text/css">
    
</style>
<html>
<body>
	<%@ include file="/WEB-INF/jsp/useredit.jsp"%>
	<%@ include file="/WEB-INF/jsp/useradd.jsp"%>
	<table id="example" class="display" width="100%" cellspacing="0">
		<thead>
			<tr>
<!-- 				<th><input name="select_all" value="1" type="checkbox" onclick="check_all(this,'confirm')"></th> -->
				<th>ROW_ID</th>
				<th><input name="select_all" value="1" type="checkbox"></th>
				<th>ID</th>
				<th>ACCOUNT</th>
				<th>USER_NAME</th>
				<th>STATUS</th>
				<th>EMAIL</th>
				<th>REGISTER_DATE</th>
				<th>LASTACTIVE_DATE</th>
				<th>Edit</th>
			</tr>
		</thead>
	</table>
</body>
</html>
<script type="text/javascript">

	var dataSet = eval('${users}');
	var oTable;
	var dateformat;
	
	$(document).ready(function() {
	    // Array holding selected row IDs
	    var rows_selected = [];
	    var opt = {
	        "bProcessing": true,
	        "bAutoWidth": false,
	        "aaData": dataSet,
	        //"displayStart": 20,
	        "bJQueryUI": true,
	        "order": [
	            [2, "asc"]
	        ],
	        dom: 'Bfrtip',
	        buttons: [{
	            text: 'Add',
	            action: function(e, dt, node, config) {
	                addUser();
	            }
	        }, {
	            text: 'Del',
	            action: function(e, dt, node, config) {
	                delUser();
	            }
	        }],
	        "oLanguage": {
	            "sSearch": "Search"
	        },
	        // 			"dom": '<"top"i>rt<"bottom"flp><"clear">',
	        "aoColumns": [{
	                "mDataProp": null,
	                "mRender": function(data, type, full, meta) {

	                    return $(data).attr('rowId', meta.row);
	                },
	                "visible": false
	            }, {
	                "mDataProp": null,
	                "mRender": function(data, type, full) {
	                    return '<input type="checkbox" name="confirm" value="' + data.id.userId + '-' + data.id.account + '">';
	                },
	                "bSortable": false,
	                "sWidth": "1%",
	                sClass: "alignRight"
	            }, {
	                "mDataProp": null,
	                "mRender": function(data, type, full) {
	                    return data.id.userId;
	                },
	                "sWidth": "7%",
	                sClass: "alignCenter"
	            }, {
	                "mDataProp": null,
	                "mRender": function(data, type, full) {
	                    return data.id.account;
	                },
	                "sWidth": "13%"
	            }, {
	                "mDataProp": "userName",
	                "sWidth": "14%"
	            }, {
	                "mDataProp": "status",
	                "sWidth": "12%",
	                sClass: "alignCenter"
	            }, {
	                "mDataProp": "email",
	                "sWidth": "15%"
	            }, {
	                "mDataProp": null,
	                "mRender": function(data, type, full) {
	                    dateformat = data.registerTime;
	                    return dateformat.substr(0, 10);
	                },
	                "sWidth": "15%"
	            }, {
	                "mDataProp": null,
	                "mRender": function(data, type, full) {
	                    dateformat = data.lastActiveTime;
	                    return dateformat.substr(0, 10);
	                },
	                "sWidth": "17%"
	            }, {
	                "mDataProp": null,
	                "mRender": function(data, type, full) {
	                    return '<a href="#" id="edit' + data.id.userId + '" class=\'button\'>Edit</a>';
	                },
	                "bSortable": false,
	                "sWidth": "3%"
	            },

	        ],
	        'rowCallback': function(row, data, dataIndex) {
	            // Get row ID
	            var rowId = data.rowId;

	            // If row ID is in the list of selected row IDs
	            if ($.inArray(rowId, rows_selected) !== -1) {
	                $(row).find('input[type="checkbox"]').prop('checked', true);
	                $(row).addClass('selected');
	            }
	        }
	    }

	    oTable = $('#example').DataTable(opt);

	    // 		$('#example tbody').on( 'click', '.button', function () {
	    // 	        var data = oTable.row( $(this).parents('tr') ).data();
	    // 			$("#userEdit").dialog({open: function(event, ui) {
	    // 				$("#id\\.userId").val(data.id.userId);
	    // 				$("#id\\.account").val(data.id.account);
	    // 				$("#userName").val(data.userName);
	    // 				$("#status").val(data.status);
	    // 				$("#email").val(data.email);
	    // 	    	}});
	    // 			var info = oTable.page.info();
	    // 			$("#userEdit").dialog("open");
	    // 	    } );

	    $("#userEdit").dialog({
	        show: "blind",
	        autoOpen: false,
	        maxWidth: 400,
	        maxHeight: 500,
	        width: 400,
	        height: 490,
	        modal: true,
	        buttons: {
	            Save: function() {
	                $('#userEditForm').submit();
	            },
	            Cancel: function() {
	                $(this).dialog("close");
	            }
	        }
	    });

	    $("#userAdd").dialog({
	        show: "blind",
	        autoOpen: false,
	        maxWidth: 400,
	        maxHeight: 500,
	        width: 400,
	        height: 480,
	        modal: true,
	        buttons: {
	            Save: function() {
	                $('#userAddForm').submit();
	            },
	            Cancel: function() {
	                $(this).dialog("close");
	            }
	        }
	    });

	    // Handle click on checkbox
	    $('#example tbody').on('click', 'input[type="checkbox"]', function(e) {
	        var $row = $(this).closest('tr');

	        // Get row data
	        var data = oTable.row($row).data();

	        // Get row ID
	        var rowId = data.rowId;

	        // Determine whether row ID is in the list of selected row IDs 
	        var index = $.inArray(rowId, rows_selected);

	        // If checkbox is checked and row ID is not in list of selected row IDs
	        if (this.checked && index === -1) {
	            rows_selected.push(rowId);

	            // Otherwise, if checkbox is not checked and row ID is in list of selected row IDs
	        } else if (!this.checked && index !== -1) {
	            rows_selected.splice(index, 1);
	        }

	        if (this.checked) {
	            $row.addClass('selected');
	        } else {
	            $row.removeClass('selected');
	        }

	        // Update state of "Select all" control
	        updateDataTableSelectAllCtrl(oTable);

	        // Prevent click event from propagating to parent
	        e.stopPropagation();
	    });

	    // Handle click on table cells with checkboxes
	    $('#example').on('click', 'tbody td, thead th:first-child', function(e) {
	        $(this).parent().find('input[type="checkbox"]').trigger('click');
	    });

	    // Handle click on "Select all" control
	    $('thead input[name="select_all"]', oTable.table().container()).on('click', function(e) {
	        if (this.checked) {
	            $('#example tbody input[type="checkbox"]:not(:checked)').trigger('click');
	        } else {
	            $('#example tbody input[type="checkbox"]:checked').trigger('click');
	        }

	        // Prevent click event from propagating to parent
	        e.stopPropagation();
	    });

	    // Handle table draw event
	    oTable.on('draw', function() {
	        // Update state of "Select all" control
	        updateDataTableSelectAllCtrl(oTable);
	    });


	    function delUser() {
	        var $form = $('<form name="userDelForm" action="del" method="post"></form>');
	        $.each(rows_selected, function( index, value ) {
	        	var data = oTable.row(value).data();
	        	$form.append('<input name="delInfo" type="hidden" value="' + data.id.userId + '-' + data.id.account + '">');
	        	});
	        $form.appendTo('body').submit();
	    }
	});
	
	function addUser(){
		$("#userAdd").dialog("open");
	}

	//
	// Updates "Select all" control in a data table
	//
	function updateDataTableSelectAllCtrl(table){
	   var $table             = table.table().node();
	   var $chkbox_all        = $('tbody input[type="checkbox"]', $table);
	   var $chkbox_checked    = $('tbody input[type="checkbox"]:checked', $table);
	   var chkbox_select_all  = $('thead input[name="select_all"]', $table).get(0);

	   // If none of the checkboxes are checked
	   if($chkbox_checked.length === 0){
	      chkbox_select_all.checked = false;
	      if('indeterminate' in chkbox_select_all){
	         chkbox_select_all.indeterminate = false;
	      }

	   // If all of the checkboxes are checked
	   } else if ($chkbox_checked.length === $chkbox_all.length){
	      chkbox_select_all.checked = true;
	      if('indeterminate' in chkbox_select_all){
	         chkbox_select_all.indeterminate = false;
	      }

	   // If some of the checkboxes are checked
	   } else {
	      chkbox_select_all.checked = true;
	      if('indeterminate' in chkbox_select_all){
	         chkbox_select_all.indeterminate = true;
	      }
	   }
	}
</script>