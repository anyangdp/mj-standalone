<template>
	<el-dialog title="角色绑定" v-model="visible" :width="650" destroy-on-close @closed="$emit('closed')">
		<el-transfer
			v-model="value"
			:titles="titles"
			filterable
			:filter-method="filterMethod"
			filter-placeholder="请输入用户名"
			:data="data"
			@change="handleChange"
		/>
		<template #footer>
			<el-button @click="visible=false">取 消</el-button>
			<el-button type="primary" :loading="isSaveing" @click="submit()">保 存</el-button>
		</template>
	</el-dialog>
</template>

<script>
export default {
	emits: ['success', 'closed'],
	data() {
		return {
			visible: false,
			isSaveing: false,
			data: [],
			titles: ['未绑定用户', '已绑定用户'],
			value: [],
			filterMethod(query, item) {
				return item.label.indexOf(query) > -1
			},
			form: {
				userList: [],
				roleId: ''
			}
		}
	},
	mounted() {

	},
	methods: {
		//显示
		open() {
			this.visible = true;
			return this
		},
		generateData(records) {
			const data = []
			records.forEach((record, index) => {
				data.push({
					label: record.nickname,
					key: index,
					spell: record.id,
				})
			})
			return data
		},
		//表单提交方法
		async submit() {
			this.isSaveing = true;
			let tt = this.data
				.map((item, index1) => {
					const index2 = this.value.findIndex((v) => v === index1);
					return index2 !== -1 ? item : null;
				})
				.filter(item1 => item1 !== null).map((item2) => item2.spell);
			this.form.userList.push(...tt)
			await this.$API.system.role.bindUser.post(this.form);
			this.isSaveing = false;
			this.$message.success("操作成功")
		},
		handleChange(value, direction, movedKeys) {
			console.log("handleChange:", value, direction, movedKeys)
		},
		//表单注入数据
		async setData(data) {
			this.form.roleId = data.id
			let res = await this.$API.system.user.list.post(1, 500, {});
			this.data.push(...this.generateData(res.records))
			let res2 = await this.$API.system.role.roleUser.post({roleId: data.id});

			let tt = this.data
				.map((item1, index1) => {
					const index2 = res2.findIndex(item2 => item2.userId === item1.spell);
					return index2 !== -1 ? index1 : null;
				})
				.filter(item => item !== null);
			console.log(tt)
			this.value.push(...tt)
		}
	}
}
</script>

<style>
</style>
