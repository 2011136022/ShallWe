<template>
  <div class="user" id="join">
		<div class="wrapC table mt-3">
			<div class="middle mt-5">
				<h2 class="edit">개인정보 수정</h2>
				<hr class="line">
				<div class="form-wrap d-flex flex-column justify-content-center">    
					<!-- 자기소개 -->
					<div class="input-wrap birthday-warp">
						<div class="p-2"><label for="introduce">자기소개</label></div>
						<input v-model="editData.introduce" 
							id="introduce"
							class="editInput"
							name="introduce"
							placeholder="소개해주세요."
							type="text"/>
					</div>
					<div class="input-wrap address-warp">
						<div class="p-2"><label for="address">주소</label></div>
						<input v-model="editData.address" 
							id="address"
							class="editInput"
							name="address"
							placeholder="주소를 입력해주세요"
							type="text"/>
					</div>
					<div>
						<kakaoMap @setAddress="setAddress"/>
					</div>
				</div>
				<hr class="line">
				<button @click="editUser(editData)" class="submitButton"><span>작성완료</span></button>
			</div>
		</div>
	</div>
</template>

<script>
	const BACK_URL = process.env.VUE_APP_BACK_URL

	import kakaoMap from '@/components/articles/kakaoMap'
	import axios from 'axios'
	import { mapState, mapActions } from 'vuex'

	export default {
		name:"editUser",
		components: {
			kakaoMap,
		},
		data: function () {
			return {
				editData:{
					address:'',
					birthday:'',
					introduce: '',
					token:'',
				},
			}
		},
		methods:{
			...mapActions(['editUser','getUserData']),
			setAddress(address){
				this.editData.address=address
			},
		},
		created: function(){
			this.getUserData()
			this.editData.editDataForSend=this.userData
		},
		computed:{
			...mapState(["userData"]),
		},
	}
</script>

<style>
	@font-face { 
		font-family: 'GmarketSansBold'; 
		src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2001@1.1/GmarketSansBold.woff') format('woff'); 
		font-weight: normal; 
		font-style: normal; 
	}
	.edit{
		font-weight: 900;
		font-style: normal;
		color:#3a4049;
	}
	.editInput{
		width: 250px;
		height: 40px;
		border: 1px solid grey;
		border-radius: 5px;
		opacity: 0.5;
	}
	.form-wrap{
		justify-content: center;
		align-items: center;
	}
	.input-wrap{
		display: flex;
		margin-bottom: 10px;
	}
	.line{
		width:50%;
		margin-bottom: 20px;
		height:3px;
		border-color: black;
	}
	label{
		width: 150px;
		text-align: left;
		font-weight: 550;
		color: black;
	}
	input{
		width: 30%;
		height: 40px;
		border: 1px solid grey;
		border-radius: 5px;
		opacity: 0.5;
	}
	.submitButton{
		color: white;
		font-weight: bold;
		background: #ee6e9f;
		border: 3px solid #ee6e9f;
		opacity: 0.75;
		border-radius: 5px;
		-webkit-transition: all .5s ease;
		transition: all .5s ease;
		-webkit-transform: translate(0, 0);
		transform: translate(0, 0);
		width:18%;
		height: 42px;
		margin-top: 10px;
	}
	.submitButton:hover{
		background-color: #ee6e9f;
		opacity: 1;
		color: white ;
		text-decoration: solid;
		border: 3px solid #ee6e9f;
		-webkit-transition: all .35s ease;
		transition: all .35s ease;
	}
</style>