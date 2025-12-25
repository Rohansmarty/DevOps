	#!/usr/bin/env sh
	set -eu

	kubectl apply -f k8s/configmap.yaml
	kubectl apply -f k8s/deployment.yaml
	kubectl apply -f k8s/service.yaml
	kubectl apply -f k8s/ingress.yaml

	kubectl rollout status deployment/devops-java-app
