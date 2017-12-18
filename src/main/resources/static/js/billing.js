$(function () {

	$('#create-flat-fee-billing-record').submit(function (e) {
		e.preventDefault();

		let billingRecord = {
			amount: $('#flat-fee-billing-amount').val(),
			description: $('#ff-record-description').val(),
			client: {
				id: $('#ff-client-name').val()
			}
		};
		let headers = {
			'X-CSRF-TOKEN': $('#ff-record-csrf').val()
		};
		let settings = {
			url: '/api/flatfees',
			headers: headers,
			data: JSON.stringify(billingRecord),
			contentType: 'application/json'
		};

		$.post(settings)
			.done(function (data) {
				$('#billing-table')
					.append(`<tr>
						<td>${ data.createdBy.username}</td>
						<td>${ data.description}</td>
						<td>${ data.client.name}</td>
						<td>${ data.amount}</td>
						<td>${ data.rate}</td>
						<td>${ data.quantity}</td>
						<td>${ data.total}</td>
						</tr>`);
				$('#flat-fee-billing-amount').val('');
				$('#ff-record-description').val('');
				$('#ff-client-name').val('')
			});
	});
	$('#create-rate-based-billing-record').submit(function (e) {
		e.preventDefault();

		let billingRecord = {
			amount: $('#rate-based-billing-amount').val(),
			description: $('#rb-record-description').val(),
			client: {
				id: $('#rb-client-name').val()
			}
		};
		let headers = {
			'X-CSRF-TOKEN': $('#rb-record-csrf').val()
		};
		let settings = {
			url: '/api/ratefees',
			headers: headers,
			data: JSON.stringify(billingRecord),
			contentType: 'application/json'
		};

		$.post(settings)
			.done(function (data) {
				$('#billing-table')
					.append(`<tr>
							<td>${ data.createdBy.username}</td>
							<td>${ data.description}</td>
							<td>${ data.client.name}</td>
							<td>${ data.amount}</td>
							<td>${ data.rate}</td>
							<td>${ data.quantity}</td>
							<td>${ data.total}</td>
							</tr>`);
				$('#rate-based-billing-amount').val('');
				$('#rb-record-description').val('');
				$('#rb-client-name').val('')
			});
	});

});