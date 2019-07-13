$(document).ready(function() {
		

		$.post("Student",{comand:"getProfList"},
				function(xml) {
				
					$(xml).find("prof").each(function(){
						
						var name = $("<h3>").text($(this).find("name").text());
						var level =  $("<p>").text($(this).find("level").text());
						var instr = $("<img>").attr("class","img-fluid").attr("src",$(this).find("linkImage").text());
						
						var email= $("<span>").text($(this).find("email").text());
						var room = $("<p>").attr("class","btn btn-primary").attr({"type":"_room"}).attr("room",$(this).find("room").text()).text("Accedi")
									.on("click", function(){
									
										var r= $(this).attr("room");										
										$.ajax({
											  type: 'POST',
											  url: 'Student',
											  data: {comand : 'call', room:r},
											  success: function(response) {
											    // re-writes the entire document
											    var newDoc = document.open("text/html", "replace");
								  
											    newDoc.write(response);
											    newDoc.close();
											  }
											});
									
									});
						
						var card=$("<div>").attr("class","col-lg-4")
								.append(
										$("<div>").attr("class","our-team-main")
										.append(
												$("<div>").attr("class","team-front")
												.append(instr,name,level),
												
												$("<div>").attr("class","team-back")
												.append(email,"<br>","<br>",$("<div>").attr({"align":"center","type":"_room"})
																			.append(room)
																			
														)
																							
												)
													
										);
						
						
						$("#bacheca").append(card);
						
					});
					
					if($("p[type='_room']").length == 0){
						alert("Professori non disponibili!");
					}
						
									
				}
		);
			
		
		
		
		

});

