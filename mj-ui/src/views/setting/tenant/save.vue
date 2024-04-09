<template>
	<el-dialog :title="titleMap[mode]" v-model="visible" :width="500" destroy-on-close @closed="$emit('closed')">
		<el-form :model="form" :rules="rules" :disabled="mode=='show'" ref="dialogForm" label-width="100px"
				 label-position="left">
			<el-form-item label="名称" prop="userName">
				<el-input v-model="form.name" :disabled="mode=='edit'" placeholder="租户名称" clearable></el-input>
			</el-form-item>
			<el-form-item label="描述" prop="name">
				<el-input v-model="form.description" placeholder="租户描述" clearable></el-input>
			</el-form-item>
		</el-form>
		<template #footer>
			<el-button @click="visible=false">取 消</el-button>
			<el-button v-if="mode!='show'" type="primary" :loading="isSaveing" @click="submit()">保 存</el-button>
		</template>
	</el-dialog>
</template>

<script>

export default {
	emits: ['success', 'closed'],
	data() {
		return {
			mode: "add",
			titleMap: {
				add: '新增用户',
				edit: '编辑用户',
				show: '查看'
			},
			visible: false,
			isSaveing: false,
			//表单数据
			form: {
				id: "",
				name: "",
				description: ""
			},
			//验证规则
			rules: {
				name: [
					{required: true, message: '请输入名称'}
				]
			}
		}
	},
	mounted() {
	},
	methods: {
		//显示
		open(mode = 'add') {
			this.mode = mode;
			this.visible = true;
			return this
		},
		//表单提交方法
		submit() {
			this.$refs.dialogForm.validate(async (valid) => {
				if(valid) {
					this.isSaveing = true;
					if(this.mode == 'add') {
						await this.$API.tenant.create.post(this.form);
						this.$emit('success', this.form, this.mode)
						this.visible = false;
						this.$message.success("操作成功")
					} else if(this.mode == 'edit') {
						await this.$API.tenant.update.put(this.form);
						this.$emit('success', this.form, this.mode)
						this.visible = false;
						this.$message.success("操作成功")
					} else {
						console.log("show")
					}
				} else {
					return false;
				}
			})
		},
		//表单注入数据
		setData(data) {
			Object.assign(this.form, data)
		}
	}
}
</script>

<style>
</style>
