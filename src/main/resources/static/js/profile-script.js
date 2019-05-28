(function() {

    "use script";

    $('.edit-profile').hide();
    $('.edit-emp-profile').hide();


    let username = document.querySelector("#username").innerHTML;
    if (document.querySelector("#save") != null) {
        document.querySelector("#save").addEventListener("click", saveInternProfile);
    }
    if (document.querySelector("#save-emp") != null) {
        document.querySelector("#save-emp").addEventListener("click", saveEmployerProfile);
    }

    $('#edit').click(function(event) {
        event.preventDefault();
        $('.show-profile').hide();
        $('.edit-profile').show();
    });

    $('#edit-emp').click(function(event) {
        console.log("test");
        event.preventDefault();
        $('.show-emp-profile').hide();
        $('.edit-emp-profile').show();
    });

    // $('#save').click(function(event) {
    //     event.preventDefault();
    //     $('.edit-profile').hide();
    //     $('.show-profile').show();
    //
    // });

    //Intern profile fields
    let firstName = null;
    let lastName = null;
    let major = null;
    let minor = null;
    let field1 = 0;
    let field2 = 0;
    let field3 = 0;
    let transcriptLink = null;
    let essayLink = null;
    let saHighSchool = false;
    let firstToCollege = false;

    //Employer profile fields
    let empName = null;
    let supervisor = null;
    let email = null;
    let industry = null;
    let location = null;
    let websiteURL = null;
    let description = null;

    function saveInternProfile(event) {
        event.preventDefault();
        console.log("hello");
        setInternVars();
        console.log(document.querySelector("#firstName").innerHTML);
        fetch("/api/v1/" + username + "/intern/profile/edit", {
            method: 'PATCH',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                "first_name": firstName,
                "last_name": lastName,
                "major": major,
                "minor": minor,
                "transcript_link": transcriptLink,
                "essay_link": essayLink,
                "sa_highschool": saHighSchool,
                "first_to_college": firstToCollege
            })
        });
        $('.edit-profile').hide();
        $('.show-profile').show();
    }

    function saveEmployerProfile(event) {
        event.preventDefault();
        setEmployerVars();
        fetch("/api/v1/" + username + "/employer/profile/edit", {
            method: 'PATCH',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                "name": empName,
                "supervisor": supervisor,
                "email": email,
                "industry": industry,
                "location": location,
                "websiteUrl": websiteURL,
                "description": description
            })
        })
        $('.edit-emp-profile').hide();
        $('.show-emp-profile').show();

    }

    function setInternVars() {
        firstName = document.querySelector("#firstName").value;
        lastName = document.querySelector("#lastName").value;
        major = document.querySelector("#major").value;
        minor = document.querySelector("#minor").value;
        // console.log(document.querySelector("#field_1").value);
        // field1 = document.querySelector("#field_1").value;
        // field2 = document.querySelector("#field_2").value;
        // field3 = document.querySelector("#field_3").value;

        transcriptLink = document.querySelector("#transcript-field").value;
        essayLink = document.querySelector("#essay-field").value;
        console.log(document.querySelector("#sa_highschool").value);
        if ((document.querySelector("#sa_highschool").value) === "false") {
            saHighSchool = 0;
        } else {
            saHighSchool = 1;
        }
        if (document.querySelector("#first_to_college").value === "false") {
            firstToCollege = 0;
        } else {
            firstToCollege = 1;
        };
    }

    function setEmployerVars() {
        empName = document.querySelector("#emp-name").value;
        supervisor = document.querySelector("#supervisor").value;
        location = document.querySelector("#location").value;
        industry = document.querySelector("#industry").value;
        websiteURL = document.querySelector("#website-url").value;
        description = document.querySelector("#description").value;
    }

})();

