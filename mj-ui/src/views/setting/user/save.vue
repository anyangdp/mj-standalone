<template>
	<el-dialog :title="titleMap[mode]" v-model="visible" :width="500" destroy-on-close @closed="$emit('closed')">
		<el-form :model="form" :rules="rules" :disabled="mode=='show'" ref="dialogForm" label-width="100px"
				 label-position="left">
			<el-form-item label="头像" prop="avatar">
				<sc-upload v-model="form.avatar" title="上传头像"></sc-upload>
			</el-form-item>
			<el-form-item label="登录账号" prop="username">
				<el-input autocomplete="false" v-model="form.username" :disabled="mode=='edit'" placeholder="用于登录系统" clearable></el-input>
			</el-form-item>
			<el-form-item label="姓名" prop="nickname">
				<el-input v-model="form.nickname" placeholder="请输入完整的真实姓名" clearable></el-input>
			</el-form-item>
			<template v-if="mode=='add'">
				<!-- 登录密码 -->
				<el-form-item label="登录密码" prop="password">
					<el-input autocomplete="new-password" type="password" v-model="form.password" clearable show-password></el-input>
				</el-form-item>

				<!-- 确认密码 -->
				<el-form-item label="确认密码" prop="password2">
					<el-input autocomplete="new-password" type="password" v-model="form.password2" clearable show-password></el-input>
				</el-form-item>
			</template>
			<!--			<el-form-item label="所属部门" prop="dept">-->
			<!--				<el-cascader v-model="form.dept" :options="depts" :props="deptsProps" clearable style="width: 100%;"></el-cascader>-->
			<!--			</el-form-item>-->
			<!--			<el-form-item label="所属角色" prop="group">-->
			<!--				<el-select v-model="form.group" multiple filterable style="width: 100%">-->
			<!--					<el-option v-for="item in groups" :key="item.id" :label="item.label" :value="item.id"/>-->
			<!--				</el-select>-->
			<!--			</el-form-item>-->
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
				username: "",
				avatar: "",
				nickname: "",
				// dept: "",
				// group: []
			},
			//验证规则
			rules: {
				username: [
					{required: true, message: '请输入登录账号', trigger: 'blur'}
				],
				nickname: [
					{required: true, message: '请输入真实姓名', trigger: 'blur'}
				],
				password: [
					{required: true, message: '请输入登录密码', trigger: 'change'}
				],
				password2: [
					{required: true, message: '请再次输入密码', trigger: 'blur'},
					{
						validator: (rule, value, callback) => {
							if (this.mode === 'add' && value !== this.form.password) {
								callback(new Error('两次输入密码不一致!'));
							} else {
								callback();
							}
						},
						trigger: 'blur'
					}
				],
				// dept: [
				// 	{required: true, message: '请选择所属部门'}
				// ],
				// group: [
				// 	{required: true, message: '请选择所属角色', trigger: 'change'}
				// ]
			},
			//所需数据选项
			groups: [],
			groupsProps: {
				value: "id",
				multiple: true,
				checkStrictly: true
			},
			depts: [],
			deptsProps: {
				value: "id",
				checkStrictly: true
			}
		}
	},
	mounted() {
		// this.getGroup()
		// this.getDept()
	},
	methods: {
		//显示
		open(mode = 'add') {
			this.mode = mode;
			this.visible = true;
			// 清空密码与确认密码字段
			this.$nextTick(() => {
				if (mode === 'add') {
					this.form.password = '';
					this.form.password2 = '';
				}
			});
			return this
		},
		//加载树数据
		// async getGroup(){
		// 	var res = await this.$API.system.role.list.get();
		// 	this.groups = res.data.rows;
		// },
		// async getDept(){
		// 	var res = await this.$API.system.dept.list.get();
		// 	this.depts = res.data;
		// },
		//表单提交方法
		submit() {
			this.$refs.dialogForm.validate(async (valid) => {
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
		},
		//表单注入数据
		setData(data) {
			this.form.id = data.id
			this.form.username = data.username
			this.form.avatar = data.avatar
			this.form.nickname = data.nickname
			// this.form.group = data.group
			// this.form.dept = data.dept

			//可以和上面一样单个注入，也可以像下面一样直接合并进去
			//Object.assign(this.form, data)
		}
	}
}
</script>

<style>
</style>
