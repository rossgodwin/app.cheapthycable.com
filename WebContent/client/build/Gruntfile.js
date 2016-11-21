module.exports = function(grunt) {

	var userConfig = require('./build.config.js');

	var taskConfig = {
		pkg : grunt.file.readJSON("package.json"),

		clean : {
			options : {
				force : true
			},
			build : [ '<%= compile_dir %>', '<%= dist_dir %>' ],
			release : [ './node_modules', '<%= compile_dir %>' ]
		},

		env : {
			dev : {
				NODE_ENV : 'DEVELOPMENT'
			},

			prod : {
				NODE_ENV : 'PRODUCTION'
			}
		},

		preprocess : {
			dev : {
				files : [ {
					src : '../src/index-app-public.tpl.jsp',
					dest : '../src/index-app-public.jsp'
				}, {
					src : '../src/index-app-secure.tpl.jsp',
					dest : '../src/index-app-secure.jsp'
				} ]
			},

			prod : {
				files : [ {
					src : '../src/index-app-public.tpl.jsp',
					dest : '<%= dist_dir %>/index-app-public.jsp'
				}, {
					src : '../src/index-app-secure.tpl.jsp',
					dest : '<%= dist_dir %>/index-app-secure.jsp'
				} ]
			}
		},

		copy : {
			compile_appjs : {
				files : [ {
					expand : true,
					cwd : '.',
					src : [ '<%= app_files.js %>' ],
					dest : '<%= compile_dir %>/src/'
				} ]
			},
			
			compile_vendor_hashids : {
				files : [ {
					src : '../vendor/hashids/hashids.min.js',
					dest : '<%= compile_dir %>/vendor/hashids/hashids.min.js'
				} ]
			},

			dist_vendor_head : {
				files : [ {
					src : '../vendor/headjs/head-1.0.2.js',
					dest : '<%= dist_dir %>/assets/js/head.js'
				} ]
			},

			dist_prod_app_public : {
				files : [ {
					src : '<%= compile_dir %>/src/assets/js/boot-prod-app-public.js',
					dest : '<%= dist_dir %>/assets/js/boot-app-public.js'
				} ]
			},

			dist_prod_app_secure : {
				files : [ {
					src : '<%= compile_dir %>/src/assets/js/boot-prod-app-secure.js',
					dest : '<%= dist_dir %>/assets/js/boot-app-secure.js'
				} ]
			}
		},

		html2js : {
			app_public_templates : {
				options : {
					base : '../..',
					module : 'app.public.templates',
					singleModule : true,
					useStrict : true,
					htmlmin : {
						collapseBooleanAttributes : true,
						collapseWhitespace : true,
						removeAttributeQuotes : true,
						removeComments : true,
						removeEmptyAttributes : true,
						removeRedundantAttributes : true,
						removeScriptTypeAttributes : true,
						removeStyleLinkTypeAttributes : true
					}
				},
				src : [ '<%= app_public_files.templates %>' ],
				dest : '<%= compile_dir %>/src/app/public/AppPublicTemplates.js'
			},

			app_secure_templates : {
				options : {
					base : '../..',
					module : 'app.templates',
					singleModule : true,
					useStrict : true,
					htmlmin : {
						collapseBooleanAttributes : true,
						collapseWhitespace : true,
						removeAttributeQuotes : true,
						removeComments : true,
						removeEmptyAttributes : true,
						removeRedundantAttributes : true,
						removeScriptTypeAttributes : true,
						removeStyleLinkTypeAttributes : true
					}
				},
				src : [ '<%= app_secure_files.templates %>' ],
				dest : '<%= compile_dir %>/src/app/gu/AppSecureTemplates.js'
			}
		},

		requirejs : {
			build_app_public : {
				options : {
					baseUrl : '<%= compile_dir %>',
					mainConfigFile : '<%= compile_dir %>/src/app/login-main.js',
					name : 'src/app/login-main',
					out : '<%= compile_dir %>/app-public.js'
				},
				preserveLicenseComments : false
			},

			build_app_secure : {
				options : {
					baseUrl : '<%= compile_dir %>',
					mainConfigFile : '<%= compile_dir %>/src/app/app-secure.js',
					name : 'src/app/app-secure',
					out : '<%= compile_dir %>/app-secure.js'
				},
				preserveLicenseComments : false
			}
		},

		concat : {
			app_public_js : {
				src : [ '<%= app_public_vendor_files.js %>',
						'<%= compile_dir %>/app-public.js' ],
				dest : '<%= dist_dir %>/app-public.js'
			},

			app_secure_js : {
				src : [ '<%= app_secure_vendor_files.js %>',
						'<%= compile_dir %>/app-secure.js' ],
				dest : '<%= dist_dir %>/app-secure.js'
			}
		},

		ngAnnotate : {
			dist_app_secure : {
				files : {
					'<%= dist_dir %>/app-secure.js' : [ '<%= dist_dir %>/app-secure.js' ]
				}
			}
		},

		uglify : {
			app_public : {
				options : {
					ASCIIOnly : true
				},
				files : {
					'<%= concat.app_public_js.dest %>' : '<%= concat.app_public_js.dest %>'
				}
			},

			app_secure : {
				options : {
					ASCIIOnly : true
				},
				files : {
					'<%= concat.app_secure_js.dest %>' : '<%= concat.app_secure_js.dest %>'
				}
			}
		},

		less : {
			app_public : {
				files : {
					'<%= compile_dir %>/assets/app-public.css' : '<%= app_public_files.less %>'
				},
				options : {
					cleancss : true,
					compress : true
				}
			},

			app_secure : {
				files : {
					'<%= compile_dir %>/assets/app-secure.css' : '<%= app_secure_files.less %>'
				},
				options : {
					cleancss : true,
					compress : true
				}
			}
		},

		cssmin : {
			options : {
				shorthandCompacting : false,
				roundingPrecision : -1
			},
			target : {
				files : {
					'<%= dist_dir %>/assets/app-secure.min.css' : [
							'<%= app_secure_vendor_files.css %>',
							'<%= compile_dir %>/assets/app-secure.css' ],
					'<%= dist_dir %>/assets/app-public.min.css' : [ '<%= compile_dir %>/assets/app-public.css' ]
				}
			}
		}
	};

	grunt.initConfig(grunt.util._.extend(taskConfig, userConfig));

	grunt.loadNpmTasks('grunt-contrib-clean');
	grunt.loadNpmTasks('grunt-contrib-copy');
	grunt.loadNpmTasks('grunt-contrib-requirejs');
	grunt.loadNpmTasks('grunt-html2js');
	grunt.loadNpmTasks('grunt-contrib-less');
	grunt.loadNpmTasks('grunt-contrib-concat');
	grunt.loadNpmTasks('grunt-contrib-uglify');
	grunt.loadNpmTasks('grunt-env');
	grunt.loadNpmTasks('grunt-preprocess');
	grunt.loadNpmTasks('grunt-ng-annotate');
	grunt.loadNpmTasks('grunt-contrib-cssmin');

	grunt.registerTask('dev-index', [ 'env:dev', 'preprocess:dev' ]);
	grunt.registerTask('prod-index', [ 'env:prod', 'preprocess:prod' ]);
	grunt.registerTask('prod-clean', [ 'clean:release' ]);

	grunt.registerTask('build-common', [ 'clean:build', 'env:prod',
			'preprocess:prod', 'copy:compile_appjs', 'copy:compile_vendor_hashids', 'copy:dist_vendor_head' ]);

	grunt.registerTask('build-app-public', [ 'copy:dist_prod_app_public',
			'html2js:app_public_templates', 'requirejs:build_app_public',
			'concat:app_public_js', 'uglify:app_public', 'less:app_public' ]);

	grunt.registerTask('build-app-secure', [ 'copy:dist_prod_app_secure',
			'html2js:app_secure_templates', 'requirejs:build_app_secure',
			'concat:app_secure_js', 'ngAnnotate:dist_app_secure',
			'uglify:app_secure', 'less:app_secure', 'cssmin' ]);

	grunt.registerTask('build-app', [ 'prod-index', 'build-common', 'build-app-public',
			'build-app-secure' ]);
};