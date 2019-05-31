
function changeLocale() {
    const formData = new FormData();
    formData.append('command', 'changeLocale');
    formData.append('sectionsIds', this.getAttribute('locale'));

    var url = '/udacidy/';
    var fetchOptions = {
        method: 'POST',
        cache: 'no-store',
        body: formData,
    };
    var responsePromise = fetch(url, fetchOptions);
    responsePromise
        .then(function (response) {
            return response.blob();
        })
        .then(function (jsonObj) {
            location.reload();
        });
}
