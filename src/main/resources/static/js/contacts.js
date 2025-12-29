console.log("contacts.js");

const baseURL = "http://localhost:8080";

const viewContactModal = document.getElementById('view_contact_modal');

// options with default values
const options = {
    placement: 'bottom-right',
    backdrop: 'dynamic',
    backdropClasses:
        'bg-gray-900/50 dark:bg-gray-900/80 fixed inset-0 z-40',
    closable: true,
    onHide: () => {
        console.log('modal is hidden');
    },
    onShow: () => {
        console.log('modal is shown');
    },
    onToggle: () => {
        console.log('modal has been toggled');
    },
};

// instance options object
const instanceOptions = {
  id: 'view_contact_modal',
  override: true
};

const contactModal = new Modal(viewContactModal, options, instanceOptions);

function openContactModel()
{
	contactModal.show();
}

function closeContactModel()
{
	contactModal.hide();
}

async function loadContactdata(id)
{
	console.log(id);
	
	try
	{
		const data = await(
							(await fetch(`${baseURL}/api/contacts/${id}`))
						  ).json();
		console.log(data);
		
		document.querySelector("#contact_name").innerHTML = data.name;
		document.querySelector("#contact_email").innerHTML = data.email;
		document.querySelector("#contact_image").src = data.picture;
		document.querySelector("#contact_phone").innerHTML = data.phoneNumber;
		document.querySelector("#contact_address").innerHTML = data.address;
		document.querySelector("#contact_about").innerHTML = data.description;

		//is favorite contact or not	
		const contactFavorite = document.querySelector("#contact_favorite");
		if(data.favorite)
		{
			contactFavorite.innerHTML = "Favorite contact";
		}
		else
		{
			contactFavorite.innerHTML = "Not favorite contact";
		}
		
		//contact website
		const contactwebsite = document.querySelector("#contact_website");
		if(data.websiteLink)
		{
			contactwebsite.href = data.websiteLink;
			contactwebsite.innerHTML = data.websiteLink;		}
		else
		{
			contactwebsite.innerHTML = "Website Not Provided";
		}
		
		//contact linkedin
		const contactlinkedin = document.querySelector("#contact_linkedin");
		if(data.linkedinLink)
		{
			contactlinkedin.href = data.linkedinLink;
			contactlinkedin.innerHTML = data.linkedinLink;		}
		else
		{
			contactlinkedin.innerHTML = "Linkedin Not Provided";
		}		
		
		openContactModel();
		
	}
	catch(error)
	{
		console.log("Error while fetching: " , error);
	}
	
}


//Delete Contact -------------------------------------------------------------

const deleteContactModal = document.getElementById('delete_contact_modal');

// options with default values
const options2 = {
    placement: 'bottom-right',
    backdrop: 'dynamic',
    backdropClasses:
        'bg-gray-900/50 dark:bg-gray-900/80 fixed inset-0 z-40',
    closable: true,
    onHide: () => {
        console.log('modal is hidden');
    },
    onShow: () => {
        console.log('modal is shown');
    },
    onToggle: () => {
        console.log('modal has been toggled');
    },
};
		
// instance options object
const instanceOptions2 = {
  id: 'delete_contact_modal',
  override: true
};

const deleteModal = new Modal(deleteContactModal, options2, instanceOptions2);

let contactIdToDelete = null;

function openDeleteModel(id)
{
	contactIdToDelete = id;
	deleteModal.show();
}

function closeDeleteModel()
{
	contactIdToDelete = null;
	deleteModal.hide();
}

document.getElementById("confirmDeleteBtn").addEventListener("click", () => {
    if (contactIdToDelete) {
        // Redirect to backend delete URL
        window.location.href = `${baseURL}/user/contacts/delete/${contactIdToDelete}`;
    }
});
