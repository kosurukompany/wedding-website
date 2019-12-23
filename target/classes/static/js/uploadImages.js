var bgPicture = document.getElementById('picture1');
bgPicture.addEventListener('change', handleBgPicture, false);

function handleBgPicture(e) {
    var reader = new FileReader();
    reader.onload = function(event) {

        $('.image1').attr('src', event.target.result);
    }
    reader.readAsDataURL(e.target.files[0]);
}