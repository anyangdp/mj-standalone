<template>
	<el-dialog :title="titleMap[mode]" v-model="visible" :width="500" destroy-on-close @closed="$emit('closed')">
		<el-form :model="form" :rules="rules" :disabled="mode=='show'" ref="dialogForm" label-width="100px" label-position="left">
			<el-form-item label="角色名称" prop="name">
				<el-input v-model="form.name" clearable></el-input>
			</el-form-item>
			<el-form-item label="是否有效" prop="active">
				<el-switch v-model="form.active" :active-value="true" :inactive-value="false"></el-switch>
			</el-form-item>
			<el-form-item label="备注" prop="description">
				<el-input v-model="form.description" clearable type="textarea"></el-input>
			</el-form-item>
		</el-form>
		<template #footer>
			<el-button @click="visible=false" >取 消</el-button>
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
					add: '新增',
					edit: '编辑',
					show: '查看'
				},
				visible: false,
				isSaveing: false,
				//表单数据
				form: {
					id:"",
					name: "",
					active: false,
					description: ""
				},
				//验证规则
				rules: {
					name: [
						{required: true, message: '请输入角色名称'}
					]
				}
			}
		},
		mounted() {

		},
		methods: {
			//显示
			open(mode='add'){
				this.mode = mode;
				this.visible = true;
				return this
			},
			//表单提交方法
			submit(){
				this.$refs.dialogForm.validate(async (valid) => {
					if(valid) {
						this.isSaveing = true;
						if(this.mode == 'add') {
							await this.$API.system.role.create.post(this.form);
							this.$emit('success', this.form, this.mode)
							this.visible = false;
							this.$message.success("操作成功")
						} else if(this.mode == 'edit') {
							await this.$API.system.role.update.put(this.form);
							this.$emit('success', this.form, this.mode)
							this.visible = false;
							this.$message.success("操作成功")
						}
					} else {
						return false;
					}
				})
			},
			//表单注入数据
			setData(data){
				console.log("data: ", data)
				Object.assign(this.form, data)
			}
		}
	}
</script>

<style>
</style>
