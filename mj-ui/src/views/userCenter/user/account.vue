<template>
	<el-alert title="异步组件动态加载使用了正处于试验阶段的<Suspense>组件, 其API和使用方式可能会改变. <Suspense> is an experimental feature and its API will likely change." type="warning" show-icon style="margin-bottom: 15px;"/>

	<el-card shadow="never" header="个人信息">
		<el-form ref="form" :model="form" label-width="120px" style="margin-top:20px;">
			<el-form-item label="账号">
				<el-input v-model="form.username" disabled></el-input>
				<div class="el-form-item-msg">账号信息用于登录，系统不允许修改</div>
			</el-form-item>
			<el-form-item label="姓名">
				<el-input v-model="form.nickname"></el-input>
			</el-form-item>
			<el-form-item label="头像" prop="avatar">
				<sc-upload v-model="form.avatar" title="上传头像"></sc-upload>
			</el-form-item>
			<el-form-item>
				<el-button type="primary">保存</el-button>
			</el-form-item>
		</el-form>
	</el-card>
</template>

<script>
	export default {
		created() {
			var userInfo = this.$TOOL.data.get("USER_INFO");
			this.form.username = userInfo.username;
			this.form.nickname = userInfo.nickname;
			this.form.id = userInfo.id;
			this.form.avatar = userInfo.avatar;
		},
		data() {
			return {
				form: {
					id: "",
					username: "",
					nickname: "",
					avatar: "",
				}
			}
		},
		methods: {
			submit() {
				this.$refs.form.validate(async (valid) => {
					if(valid) {
						this.isSaveing = true;
						if(this.mode == 'add') {
							await this.$API.system.user.create.post(this.form);
							this.$emit('success', this.form, this.mode)
							this.visible = false;
							this.$message.success("操作成功")
						} else if(this.mode == 'edit') {
							await this.$API.system.user.update.put(this.form);
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
			}
		}
	}
</script>

<style>
</style>
