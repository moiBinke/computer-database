$(function() {
  
  $("form[name='addComputer']").validate({
    rules: {
	      computerName:"required",
    	  
    },
    messages: {

      computerName: "Please Computer Name is required",
      
    },
  submitHandler: function(form) {
      form.submit();
    }
  });
});
