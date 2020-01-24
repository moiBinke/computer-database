$(function() {
  
  $("form[name='addComputer']").validate({
    rules: {
	      computerName: "required",
	      computerCompany:"required",
    	  introduced:"required",
    	  discontinued:"required"
    	  
    },
    // Specify validation error messages
    messages: {
      computerName: "Please enter computer name",
      computerCompany: "Please select this computer company",
      introduced:"Please enter introduced date",
	  discontinued:"please enter discontinued date"
    },
  submitHandler: function(form) {
      form.submit();
    }
  });
});
