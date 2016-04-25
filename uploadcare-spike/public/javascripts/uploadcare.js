$(function() {
    var widget = uploadcare.initialize('#uploadcare-form')[0];
    widget.onUploadComplete(function(info) {
       console.log('upload completed', info);
       var uploadResult = $('.upload-result');
       uploadResult.html("<div>File uploaded successfully: <a href=' " + info.cdnUrl + "'>" + info.name + "</a></div>");
       uploadResult.show();
    });
});
