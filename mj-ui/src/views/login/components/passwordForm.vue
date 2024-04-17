<template>
	<el-form ref="loginForm" :model="form" :rules="rules" label-width="0" size="large">
		<el-form-item prop="username">
			<el-input v-model="form.username" prefix-icon="el-icon-user" clearable
					  :placeholder="$t('login.userPlaceholder')"></el-input>
		</el-form-item>
		<el-form-item prop="password">
			<el-input v-model="form.password" prefix-icon="el-icon-lock" clearable show-password
					  :placeholder="$t('login.PWPlaceholder')"></el-input>
		</el-form-item>
		<!--		<el-form-item style="margin-bottom: 10px;">-->
		<!--				<el-col :span="12">-->
		<!--					<el-checkbox :label="$t('login.rememberMe')" v-model="form.autologin"></el-checkbox>-->
		<!--				</el-col>-->
		<!--				<el-col :span="12" class="login-forgot">-->
		<!--					<router-link to="/reset_password">{{ $t('login.forgetPassword') }}？</router-link>-->
		<!--				</el-col>-->
		<!--		</el-form-item>-->
		<el-form-item>
			<el-button type="primary" style="width: 100%;" :loading="islogin" round @click="login">{{
					$t('login.signIn')
				}}
			</el-button>
		</el-form-item>
		<!--		<div class="login-reg">-->
		<!--			{{$t('login.noAccount')}} <router-link to="/user_register">{{$t('login.createAccount')}}</router-link>-->
		<!--		</div>-->
	</el-form>
</template>

<script>
export default {
	data() {
		return {
			userType: 'admin',
			form: {
				username: "",
				password: "",
				// autologin: false
			},
			rules: {
				user: [
					{required: true, message: this.$t('login.userError'), trigger: 'blur'}
				],
				password: [
					{required: true, message: this.$t('login.PWError'), trigger: 'blur'}
				]
			},
			islogin: false,
		}
	},
	mounted() {

	},
	methods: {
		async login() {

			var validate = await this.$refs.loginForm.validate().catch(() => {
			})
			if(!validate) {
				return false
			}

			this.islogin = true
			var data = {
				username: this.form.username,
				password: this.form.password
			}
			console.log(data)
			try {
				//获取token
				var token = await this.$API.auth.token.post(data)
				this.$TOOL.cookie.set("TOKEN", token, {})
				let user = await this.$API.auth.userInfo.get()
				console.log("user:", user)
				this.$TOOL.data.set("USER_INFO", user)
				let authorities = []
				user.authorities.forEach((item) => {
					authorities.push(item.authority)
				})
				user.role.forEach((item) => {
					authorities.push(item)
				})
				console.log("authorities:", authorities)
				this.$TOOL.data.set("USER_INFO_AUTHORITIES", authorities)

				this.$router.replace({
					path: '/'
				})
				this.$message.success("Login Success 登录成功")
			} finally {
				this.islogin = false
			}

		},
	}
}
</script>

<style>
</style>
