var mainArea = {
    selector: '#mytextarea',
    height: 700,
    plugins: [
        "advlist autolink lists link image charmap print preview anchor media mediaembed template",
        "searchreplace visualblocks code fullscreen",
        "insertdatetime media table paste imagetools wordcount checklist"
    ],
    content_css: [
        '//fonts.googleapis.com/css?family=Lato:300,300i,400,400i',
        '//www.tiny.cloud/css/codepen.min.css'
    ],
    mobile: {
        theme: 'mobile',
        plugins: ['autosave', 'lists', 'autolink'],
        toolbar: ['undo', 'bold', 'italic', 'styleselect']
    },
    toolbar: ' insertfile undo redo | styleselect | bold italic strikethrough forecolor backcolor permanentpen formatpainter | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | link image media pageembed | checklist',
};

tinymce.init(mainArea);