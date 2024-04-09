import tool from '@/utils/tool';

export function permission(data) {
	let permissions = tool.data.get("USER_INFO_AUTHORITIES");
	if(!permissions){
		return false;
	}
	let isHave = permissions.includes(data);
	console.log("isHave:", isHave)
	console.log("data:", data)
	return isHave;
}

export function rolePermission(data) {
	let authorities = tool.data.get("USER_INFO_AUTHORITIES");
	if(!authorities){
		return false;
	}
	let role = authorities;
	if(!role){
		return false;
	}
	let isHave = role.includes(data);
	console.log("isHave:", isHave)
	console.log("data:", data)
	return isHave;
}
