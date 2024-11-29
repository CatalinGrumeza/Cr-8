/**
 * 
 */
const div=document.querySelector("div");

const url="http://localhost:8080/api/index"
fetch(url,
	{
		method:"GET",
		headers:{}
	}).then(async response=>{
		let risposta=await response.json();
		risposta
		div.textContent=`${risposta[0].email}
		 ${risposta[1].email}`;
	})
	
	