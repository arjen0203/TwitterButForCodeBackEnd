#!/bin/sh
RG_NAME="code-x"

# Set script to exit on error
set -e
# Login to Azure
# https://docs.microsoft.com/en-us/cli/azure/reference-index?view=azure-cli-latest#az_login
az login
# Delete our resource group and everything attached
# https://docs.microsoft.com/en-us/cli/azure/group?view=azure-cli-latest#az_group_delete
az group delete -n $RG_NAME -y --no-wait
