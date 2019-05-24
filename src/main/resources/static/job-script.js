(function() {

    "use script";

    let username = document.querySelector("#username").innerHTML;
    let job = document.querySelector("#job").innerHTML;
    let title = null;
    let companyName = null;
    let industry = null;
    let location = null;
    let jobDescription = null;
    let responsibilities = null;
    let basicQualifications = null;
    let aboutUs = null;


    document.querySelector("#apply").addEventListener("click", applyToJob);
    document.querySelector("#delete").addEventListener("click", deleteJob);
    document.querySelector("#edit").addEventListener("click", editJob);


    function applyToJob(event) {
        event.preventDefault();
        fetch("/api/v1/" + username + "/add/" + job).then(response => {
            response.json().then( data => {
                console.log(data);
            })
        })
    }

    function deleteJob(event) {
        event.preventDefault();
        fetch("/api/v1/" + username + "/employer/delete/" + job, {
            method: 'DELETE',
            headers: { 'Content-Type': 'application/json' }
        }).then(response => {
            response.json().then( data => {
                console.log(data);
            })
        })
    }

    function editJob(event) {
        setJobVars();
        event.preventDefault();
        fetch("/api/v1/" + username + "/employer/profile/edit", {
            method: 'PATCH',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                "title": title,
                "companyName": companyName,
                "location": location,
                "jobDescription": jobDescription,
                "responsibilities": responsibilities,
                "basicQualifications": basicQualifications,
                "aboutUs": aboutUs
            })
        })
    }

    function setJobVars() {
        title = document.querySelector("#title").innerHTML;
        companyName = document.querySelector("#company-name").innerHTML;
        industry = document.querySelector("#industry").innerHTML;
        location = document.querySelector("#location").innerHTML;
        jobDescription = document.querySelector("#job-description").innerHTML;
        responsibilities = document.querySelector("#responsibilities").innerHTML;
        basicQualifications = document.querySelector("#basic-qualifications").innerHTML;
        aboutUs = document.querySelector("#about-us").innerHTML;
    }


})();